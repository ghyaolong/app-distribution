package com.cube.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cube.annotation.Login;
import com.cube.common.exception.RRException;
import com.cube.common.validator.ValidatorUtils;
import com.cube.config.WebConfig;
import com.cube.entity.TokenEntity;
import com.cube.exception.RRExceptionHandler;
import com.cube.service.TokenService;
import com.cube.utils.*;
import com.cube.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import com.cube.entity.MemberEntity;
import com.cube.service.MemberService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;
import org.springframework.web.multipart.MultipartFile;
import sun.dc.pr.PRException;

import javax.validation.Valid;


/**
 * 会员表
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:35:42
 */
@Slf4j
@RestController
@RequestMapping("cube/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    private RedisTemplate redis;

    @Autowired
    private PathManager pathManager;



    @Autowired
    private TokenService tokenService;

    //过期时间3600s
    private Integer expire = 3600;

    @Autowired
    public void setRedis(@Qualifier("redisTemplate") RedisTemplate redis) {
        this.redis = redis;
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //泛型设置成Long后必须更改对应的序列化方案
//        redis.setKeySerializer(new JdkSerializationRedisSerializer());
        redis.setValueSerializer(jackson2JsonRedisSerializer);
        redis.setKeySerializer(new StringRedisSerializer());
        redis.afterPropertiesSet();
    }

    @PostMapping("/login")
    public Result login(String userName,String password){
        if(StringUtils.isEmpty(userName)){
            throw new RRException("用户名不能为空");
        }

        if(!MyValidatorUtils.isEmail(userName)){
            throw new RRException("请输入正确的邮箱地址");
        }

        if(StringUtils.isEmpty(password)){
            throw new RRException("密码不能为空");
        }

        //判断密码是否正确
        MemberEntity one = memberService.getOne(new QueryWrapper<MemberEntity>().lambda().eq(MemberEntity::getUserName, userName));
        if(one!=null){
            if(PasswordUtils.getMD5(password).equals(one.getPassword())){
                //登录成功 产生token
                Map<String,Object> map = new HashMap<>();
                map.put("userId",one.getId());
                map.put("userName",one.getUserName());
                String token = JwtUtil.getTokenByJson(map, WebConfig.Token_EncryKey, WebConfig.Token_SecondTimeOut);
                return ResultUtils.ok(token);
            }else{
                throw new RRException("用户名或密码不正确");
            }
        }
        return ResultUtils.error("500","登录失败");
    }

    @PostMapping("/register")
    public Result register(@Valid MemberVo memberVo,String code){
        if(StringUtils.isEmpty(memberVo.getUserName())){
            throw new RRException("用户名不能为空");
        }
        MemberEntity one = memberService.getOne(new QueryWrapper<MemberEntity>().lambda().eq(MemberEntity::getUserName, memberVo.getUserName()));
        if(one !=null){
            throw new RRException("用户名已存在");
        }
        if(!MyValidatorUtils.isEmail(memberVo.getUserName())){
            throw new RRException("请输入正确的邮箱地址");
        }

        if(StringUtils.isEmpty(memberVo.getPassword())){
            throw new RRException("密码不能为空");
        }

        if(StringUtils.isEmpty(code)){
            throw new RRException("请输入手机验证码");
        }

        if(StringUtils.isEmpty(memberVo.getTel())){
            throw new RRException("请输入手机号码");
        }

        String enCode = (String) redis.boundValueOps("1" + memberVo.getTel()).get();
        if(!code.equals(enCode)){
            throw new RRException("验证码错误");
        }

        String md5Password = PasswordUtils.getMD5(memberVo.getPassword());

        memberVo.setPassword(md5Password);



        MemberEntity memberEntity = MyBeanUtils.copy(memberVo, MemberEntity.class);
        memberService.save(memberEntity);

        return ResultUtils.okMsg("注册成功");
    }



    /**
     * 检查注册的用户名(邮箱)是否存在
     * @param userName 邮箱
     * @return
     */
    @GetMapping("/existUserName/{userName}")
    public Result existUserName(@PathVariable("userName") String userName){
        //验证是是否是邮箱
        MemberEntity one = memberService.getOne(new QueryWrapper<MemberEntity>().lambda().eq(MemberEntity::getUserName, userName));
        if(one!=null){
            return ResultUtils.error("500","已存在");
        }else{
            return ResultUtils.okMsg("");
        }
    }

    @GetMapping("/sendCode/{phone}")
    public Result sendPhoneCode(@PathVariable("phone") String phone){
        String existCode = (String) redis.boundValueOps("1" + phone).get();
        if(!StringUtils.isEmpty(existCode)){
            throw new RRException("已发送，请稍后再试");
        }
        String phoneCode = CodeGenerator.phoneCode(6);

        //todoycl 发送手机验证码到短信平台
        // code here

        log.info("手机号={}生成验证码= {}",phone,phoneCode);
        redis.boundValueOps("1"+phone).set(phoneCode,60, TimeUnit.SECONDS);
        return ResultUtils.okMsg("验证码发送成功");
    }


    /**
     * 上传证件照
     * @param userId
     * @param file
     * @param type  0:正面    1：背面   2：手持
     * @return
     */
    @Login
    @PostMapping("/upload")
    public Result uploadCertificate(@RequestAttribute("userId")String userId,@RequestParam("file") MultipartFile file,String type){

        if(StringUtils.isEmpty(type)){
            throw new RRException("type参数不能为空");
        }
        //获取上传路径static/upload/
        String certificateUpload = PathManager.getUploadPath("certificate"+File.separator+userId+File.separator+type);
        //转存到临时目录
        //String filePath = transfer(file,certificateUpload);
        // 获取文件后缀
        String fileName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(fileName);
        // 生成文件名
        String newFileName = UUID.randomUUID().toString() + "." + ext;
        // 转存到 tmp
        String destPath = certificateUpload + File.separator + newFileName;
        destPath = destPath.replaceAll("//", "/");
        try {
            file.transferTo(new File(destPath));
        } catch (IOException e) {
            log.error("上传认证图片失败{}",e);
            throw new RRException("上传失败");
        }
        String path = "certificate"+File.separator+userId+File.separator+type+File.separator+newFileName;
        path = path.replace("\\", "/");
        UpdateWrapper<MemberEntity> memberUW = new UpdateWrapper<>();
        memberUW.lambda().eq(MemberEntity::getId, userId);

        if("0".equals(type)){
            memberUW.setSql(" front_photo_path = '"+path+"'");
        }else if("1".equals(type)){
            memberUW.setSql(" back_photo_path = '"+path+"'");
        }else if("2".equals(type)){
            memberUW.setSql(" hand_photo_path = '"+path+"'" );
        }
        memberService.update(memberUW);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("baseUrl",this.pathManager.getBaseURL(false)+path);
        resultMap.put("oriPath",destPath);
        return ResultUtils.ok(resultMap);
    }
    /**
     * 实名认证
     * @param userId
     * @param memberVo
     * @return
     */
    @Login
    @PostMapping("/authRealName")
    public Result authRealName(@RequestAttribute("userId") String userId, MemberVo memberVo){
        if(StringUtils.isEmpty(memberVo.getRealName())){
            throw new RRException("真实姓名不能为空");
        }
        if(StringUtils.isEmpty(memberVo.getIdNumber())){
            throw new RRException("身份证号不能为空");
        }else if(!MyValidatorUtils.isIDNumber(memberVo.getIdNumber())){
            throw new RRException("身份证号码格式不正确");
        }

        if(StringUtils.isEmpty(memberVo.getAddress())){
            throw new RRException("地址不能为空");
        }
        MemberEntity memberEntity = memberService.getById(userId);
        if(StringUtils.isEmpty(memberEntity.getFrontPhotoPath())){
            throw new RRException("请上传证件正面照");
        }
        if(StringUtils.isEmpty(memberEntity.getBackPhotoPath())){
            throw new RRException("请上传证件背面照");
        }
        if(StringUtils.isEmpty(memberEntity.getHandPhotoPath())){
            throw new RRException("请上传手持证件照");
        }
        MyBeanUtils.copyIgnoreNull(memberVo, memberEntity);
        memberEntity.setStatus("1");
        memberService.updateById(memberEntity);
        return ResultUtils.ok();
    }

    /**
     *
     * @param userId
     * @param currPass 当前密码
     * @param newPass  新密码
     * @param confirmPass 确认密码
     * @return
     */
    @Login
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestAttribute("userId")String userId,String currPass,String newPass,String confirmPass){
        if(StringUtils.isEmpty(currPass)){
            throw new RRException("原密码不能为空");
        }
        if(StringUtils.isEmpty(newPass)){
            throw new RRException("新密码不能为空");
        }
        if(StringUtils.isEmpty(confirmPass)){
            throw new RRException("确认密码不能为空");
        }
        if(!newPass.equals(confirmPass)){
            throw new RRException("两次密码输入不一致");
        }

        MemberEntity memberEntity = memberService.getById(userId);
        String md5Password = PasswordUtils.getMD5(currPass);
        if(!memberEntity.getPassword().equals(md5Password)){
            throw new RRException("原密码不正确");
        }
        String md5NewPass = PasswordUtils.getMD5(newPass);
        memberEntity.setPassword(md5NewPass);
        memberService.updateById(memberEntity);
        return ResultUtils.okMsg("修改成功");
    }


    /**
     * 修改个人资料
     * @param memberVo
     * @return
     */
    @Login
    @PostMapping("/updateMemberInfo")
    public Result updateMemberInfo(@RequestAttribute("userId")String userId, MemberVo memberVo){
        if(StringUtils.isEmpty(memberVo.getTel())){
            throw new RRException("手机号不能为空");
        }
        memberVo.setId(userId);
        MemberEntity memberEntity = MyBeanUtils.copy(memberVo, MemberEntity.class);
        memberService.updateById(memberEntity);
        return ResultUtils.okMsg("修改成功");
    }


    /**
     * 转存文件
     * @param srcFile
     * @return
     */
    private String transfer(MultipartFile srcFile,String path) {
        try {
            // 获取文件后缀
            String fileName = srcFile.getOriginalFilename();
            String ext = FilenameUtils.getExtension(fileName);
            // 生成文件名
            String newFileName = UUID.randomUUID().toString() + "." + ext;
            // 转存到 tmp
            String destPath = path + File.separator + newFileName;
            destPath = destPath.replaceAll("//", "/");
            srcFile.transferTo(new File(destPath));
            return destPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

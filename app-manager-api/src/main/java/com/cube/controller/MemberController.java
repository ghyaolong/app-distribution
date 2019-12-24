package com.cube.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cube.annotation.Login;
import com.cube.common.exception.RRException;
import com.cube.config.WebConfig;
import com.cube.entity.MemberEntity;
import com.cube.service.MemberService;
import com.cube.service.TokenService;
import com.cube.utils.*;
import com.cube.utils.sms.SMSUtils;
import com.cube.vo.MemberVo;
import com.google.code.kaptcha.Producer;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


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
    private Producer producer;

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;



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
            if(EncryptAndDeEncryptUtils.getMD5(password).equals(one.getPassword())){
                //登录成功 产生token
                Map<String,Object> map = new HashMap<>();
                map.put("userId",one.getId());
                map.put("userName",one.getUserName());
                String token = JwtUtil.getTokenByJson(map, WebConfig.Token_EncryKey, WebConfig.Token_SecondTimeOut);
                return ResultUtils.ok(token);
            }else{
                throw new RRException("密码不正确");
            }
        }else{
            throw new RRException("用户名不存在");
        }
    }

    /**
     *
     * @param memberVo
     * @param code
     * @param uuid 用于区分验证码的唯一标识
     * @return
     */
    @PostMapping("/register")
    public Result register(@Valid MemberVo memberVo,String code,String uuid){
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

        String validCode = (String) redis.boundValueOps("2" + uuid).get();
        if(!validCode.equalsIgnoreCase(memberVo.getValidCode())){
            throw new RRException("验证码不正确");
        }
        redis.delete("2"+uuid);
        if(StringUtils.isEmpty(code)){
            throw new RRException("请输入手机验证码");
        }

        if(StringUtils.isEmpty(memberVo.getTel())){
            throw new RRException("请输入手机号码");
        }

        String enCode = (String) redis.boundValueOps("1" + memberVo.getTel()).get();
        if(!code.equals(enCode)){
            throw new RRException("手机验证码错误");
        }
        String md5Password = EncryptAndDeEncryptUtils.getMD5(memberVo.getPassword());
        memberVo.setPassword(md5Password);

        MemberEntity memberEntity = MyBeanUtils.copy(memberVo, MemberEntity.class);
        memberService.save(memberEntity);

        return ResultUtils.okMsg("注册成功");
    }



    /**
     * 获取个人信息
     * @param userId
     * @return
     */
    @Login
    @PostMapping("/getUserInfo")
    public Result getUserInfo(@RequestAttribute("userId")String userId){
        MemberEntity memberEntity = memberService.getById(userId);
        memberEntity.setAvatar(pathManager.getBaseURL(false)+memberEntity.getAvatar());
        MemberVo copy = MyBeanUtils.copy(memberEntity, MemberVo.class, new String[]{"password"});
        return ResultUtils.ok(copy);
    }


    /**
     *
     * @param phone
     * @param code
     * @param password
     * @param configPassword
     * @return
     */
//    @PostMapping("/resetPassword")
//    public Result resetPassword(String phone,String code,String password,String configPassword){
//        if(StringUtils.isEmpty(phone)){
//            throw new RRException("手机号不能为空");
//        }
//        MemberEntity one = memberService.getOne(new QueryWrapper<MemberEntity>().lambda().eq(MemberEntity::getTel, phone));
//        if(one==null){
//            throw new RRException("没有找到使用该手机号注册的用户");
//        }
//
//        if(StringUtils.isEmpty(password)){
//            throw new RRException("密码不能为空");
//        }
//
//        if(!password.equals(configPassword)){
//            throw new RRException("两次密码输入不一致");
//        }
//
//        String enCode = (String) redis.boundValueOps("3" + phone).get();
//
//        if(!code.equals(enCode)){
//            throw new RRException("手机验证码错误");
//        }
//        boolean update = memberService.update(new UpdateWrapper<MemberEntity>().lambda().eq(MemberEntity::getTel, phone).set(MemberEntity::getPassword, password));
//        if(update){
//            return ResultUtils.okMsg("操作成功");
//        }
//        return ResultUtils.error("500","操作失败");
//    }


    /**
     * 发送重置密码邮箱
     * @param userName
     * @param request
     * @return
     */
    @PostMapping("/sendResetPassword")
    public Result sendResetPassword(String userName, HttpServletRequest request){
        if(StringUtils.isEmpty(userName)){
            throw new RRException("邮箱不能为空");
        }
        if(!MyValidatorUtils.isEmail(userName)){
            throw new RRException("请输入正确的邮箱");
        }

        MemberEntity one = memberService.getOne(new QueryWrapper<MemberEntity>().lambda().eq(MemberEntity::getUserName, userName));
        if (one == null) {
            throw new RRException("没有找到使用该邮箱注册的用户");
        }

        String sid = IDGenerator.getUUID();
        sid = EncryptAndDeEncryptUtils.getMD5(sid);
        redis.boundValueOps("2"+sid).set(userName,10,TimeUnit.MINUTES);

        String path = request.getContextPath();

        String serverIp = null;
        try {
            InetAddress localHost = InetAddress.getLocalHost();

            //todo 需要先获取配置的域名，如果域名不存在在获取ip
            serverIp = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String basePath = request.getScheme()+"://"+ serverIp+":"+request.getServerPort()+path+"/";

        //String pathUrl = basePath+"cube/member/reset_password?sid="+sid;
        String pathUrl = "http://172.20.20.3:8080/#/forgetPassword?sid="+sid;
        String text = "<b style='color:red'>找回密码连接为：</b><a href='"+pathUrl+"'>"+pathUrl+"</a> 该邮件30分钟内有效,请尽快处理!";
        try {
            sendEmail(userName,"密码找回操作",text);
        } catch (MessagingException e) {
            log.error("发送邮箱连接失败：",e);
            throw new RRException("发送邮箱连接失败");
        }
        return ResultUtils.okMsg("发送成功");
    }
    private void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    /**
     * 验证URL是否正确
     * @param sid
     * @return
     */
    @GetMapping("/reset_password")
    public Result verifyURL(String sid,HttpServletRequest request,HttpServletResponse res){

        if(StringUtils.isEmpty(sid)){
            throw new RRException("sid不能为空");
        }

        String userName = (String) redis.boundValueOps("2" + sid).get();
        if(StringUtils.isEmpty(userName)){
            throw new RRException("连接已过期，请重新发送验证邮件");
        }
        //跳转连接
        return ResultUtils.ok();
    }


    /**
     * 修改密码
     * @param sid
     * @param password
     * @param configPassword
     * @return
     */
    @PostMapping("/resetPassword")
    public Result updatePassword(String sid,String password,String configPassword){
        String userName = (String) redis.boundValueOps("2" + sid).get();
        if(StringUtils.isEmpty(userName)){
            throw new RRException("连接已过期，请重新发送验证邮件");
        }
        if(StringUtils.isEmpty(password)){
            throw new RRException("密码不能为空");
        }
        if(!password.equals(configPassword)){
            throw new RRException("两次密码输入不一致");
        }
        password = EncryptAndDeEncryptUtils.getMD5(password);
        boolean b = memberService.update(new UpdateWrapper<MemberEntity>().lambda().eq(MemberEntity::getUserName, userName).set(MemberEntity::getPassword, password));
        if(b){
            return ResultUtils.okMsg("操作成功");
        }else{
            return ResultUtils.error("500","操作失败");
        }
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

    /**
     *
     * @param phone
     * @param type  0:注册   1:找回密码
     * @return
     */
    @PostMapping("/sendCode")
    public Result sendPhoneCode(String phone,String type){
        if(StringUtils.isEmpty(phone)){
            throw new RRException("手机号不能为空");
        }

        if(!MyValidatorUtils.isPhoneNumber(phone)){
            throw new RRException("请输入正确的手机号");
        }

        if(!"0".equals(type)&&!"1".equals(type)){
            throw new RRException("请输入正确的type参数");
        }

        String phoneCode = CodeGenerator.phoneCode(6);
        List<MemberEntity> list = memberService.list(new QueryWrapper<MemberEntity>().lambda().eq(MemberEntity::getTel, phone));

        if("0".equals(type)){
            //todo 判断手机是否注册过,之后需要取消注释
//            if(one!=null){
//                throw new RRException("手机号已注册过");
//            }
            String existCode = (String) redis.boundValueOps("1" + phone).get();
            if(!StringUtils.isEmpty(existCode)){
                throw new RRException("已发送，请稍后再试");
            }
            SMSUtils.sendSms(phoneCode,phone,"86");
            log.info("注册手机号={},生成验证码= {}",phone,phoneCode);
            redis.boundValueOps("1"+phone).set(phoneCode,60, TimeUnit.SECONDS);
        }else if("1".equals(type)){
            //判断手机号是否存在
            if(!Collections.isEmpty(list)){
                throw new RRException("该手机已注册");
            }
            String existCode = (String) redis.boundValueOps("3" + phone).get();
            if(!StringUtils.isEmpty(existCode)){
                throw new RRException("已发送，请稍后再试");
            }
            SMSUtils.sendSms(phoneCode,phone,"86");
            log.info("找回密码手机号={},生成验证码= {}",phone,phoneCode);
            redis.boundValueOps("3"+phone).set(phoneCode,60, TimeUnit.SECONDS);
        }



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
        String md5Password = EncryptAndDeEncryptUtils.getMD5(currPass);
        if(!memberEntity.getPassword().equals(md5Password)){
            throw new RRException("原密码不正确");
        }
        String md5NewPass = EncryptAndDeEncryptUtils.getMD5(newPass);
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
     * 修改手机号
     * @param userId
     * @param phone
     * @param code
     * @return
     */
    @Login
    @PostMapping("/updatePhone")
    public Result updatePhone(@RequestAttribute("userId")String userId, String phone,String code){
        if(StringUtils.isEmpty(phone)){
            throw new RRException("手机号不能为空");
        }
        if(StringUtils.isEmpty(code)){
            throw new RRException("手机验证码不能为空");
        }
        String codeValue  = (String) redis.boundValueOps("3" + phone).get();
        if(!code.equalsIgnoreCase(codeValue)){
            throw new RRException("手机验证码不正确");
        }
        MemberEntity me = new MemberEntity();
        me.setId(userId);
        me.setTel(phone);
        memberService.updateById(me);
        return ResultUtils.okMsg("修改成功");
    }



    /**
     * 验证码
     */
    @PostMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 生成验证码
     * @param uuid
     * @return
     */
    private BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isEmpty(uuid)){
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        log.info("uuid={}的图片验证码是：{}",uuid,code);
        redis.boundValueOps("2"+uuid).set(code,5,TimeUnit.MINUTES);
        return producer.createImage(code);
    }

    /**
     * 修改用户头像
     * @param userId
     * @param file
     * @return
     */
    @Login
    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestAttribute("userId")String userId,@RequestParam("file") MultipartFile file){
        //获取上传路径static/upload/
        String avatarUpload = PathManager.getUploadPath("avatar"+File.separator+userId+File.separator);
        // 获取文件后缀
        String fileName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(fileName);
        // 生成文件名
        String newFileName = UUID.randomUUID().toString() + "." + ext;
        // 转存到 tmp
        String destPath = avatarUpload + File.separator + newFileName;
        destPath = destPath.replaceAll("//", "/");
        try {
            file.transferTo(new File(destPath));
        } catch (IOException e) {
            log.error("上传头像失败{}",e);
            throw new RRException("上传头像失败");
        }
        String path = "avatar"+File.separator+userId+File.separator+newFileName;
        path = path.replace("\\", "/");
        UpdateWrapper<MemberEntity> memberUW = new UpdateWrapper<>();
        memberUW.lambda().eq(MemberEntity::getId, userId);
        memberUW.setSql(" avatar = '"+path+"'" );
        memberService.update(memberUW);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("baseUrl",this.pathManager.getBaseURL(false)+path);
        resultMap.put("oriPath",destPath);
        return ResultUtils.ok(resultMap);
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

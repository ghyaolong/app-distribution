package com.cube.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cube.common.exception.RRException;
import com.cube.common.utils.EncryptAndDeEncryptUtils;
import com.cube.common.utils.IDGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cube.modules.app.entity.MemberEntity;
import com.cube.modules.app.service.MemberService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;


/**
 * 会员管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@RestController
@RequestMapping("/app/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
        if(StringUtils.isEmpty(member.getUserName())){
            throw new RRException("用户名不能为空");
        }
        if(StringUtils.isEmpty(member.getPassword())){
            throw new RRException("密码不能为空");
        }

        if(StringUtils.isEmpty(member.getTel())){
            throw new RRException("手机号不能为空");
        }

        //判断用户名是否存在

        MemberEntity one = memberService.getOne(new QueryWrapper<MemberEntity>().lambda().eq(MemberEntity::getUserName, member.getUserName()));
        if(one!=null){
            throw new RRException("用户名已存在!");
        }
        member.setId(IDGenerator.getUUID());
        String md5 = EncryptAndDeEncryptUtils.getMD5(member.getPassword());
        member.setPassword(md5);
        member.setDownloadCount(100);
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
        if(!StringUtils.isEmpty(member.getUserName())){
            throw new RRException("不能修改用户名");
        }
        String md5 = EncryptAndDeEncryptUtils.getMD5(member.getPassword());
        member.setPassword(md5);
        memberService.updateById(member);
        return R.ok();
    }

    /**
     * 修改用户审核状态
     * 0：未审核   1：审核中  2：审核通过   3：审核驳回
     * @param member
     * @return
     */
    @PostMapping("/audit")
    public R audit(@RequestBody MemberEntity member){
        if(StringUtils.isEmpty(member.getId())){
            throw new RRException("id不能为空");
        }

        if(StringUtils.isEmpty(member.getStatus())){
            throw new RRException("状态不能为空");
        }
        memberService.update(new UpdateWrapper<MemberEntity>().lambda().eq(MemberEntity::getId, member.getId()).set(MemberEntity::getStatus, member.getStatus()));
        return R.ok();
    }

    /**
     * 修改用户冻结状态
     * 0:正常   1：冻结
     * @param member
     * @return
     */
    @PostMapping("/forbidden")
    public R forbidden(@RequestBody MemberEntity member){
        if(StringUtils.isEmpty(member.getId())){
            throw new RRException("id不能为空");
        }

        if(StringUtils.isEmpty(member.getIsForbidden())){
            throw new RRException("状态不能为空");
        }
        memberService.update(new UpdateWrapper<MemberEntity>().lambda().eq(MemberEntity::getId, member.getId()).set(MemberEntity::getIsForbidden, member.getStatus()));
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

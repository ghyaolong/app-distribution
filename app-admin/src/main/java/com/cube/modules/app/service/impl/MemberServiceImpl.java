package com.cube.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.modules.app.dao.MemberDao;
import com.cube.modules.app.entity.MemberEntity;
import com.cube.modules.app.service.MemberService;
import org.springframework.util.StringUtils;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<MemberEntity> lambda = queryWrapper.lambda();
        if(!StringUtils.isEmpty(params.get("userName"))){
            lambda.like(MemberEntity::getUserName,params.get("userName"));
        }

        if(!StringUtils.isEmpty(params.get("tel"))){
            lambda.eq(MemberEntity::getTel,params.get("tel"));
        }

        if(!StringUtils.isEmpty(params.get("status"))){
            lambda.eq(MemberEntity::getStatus,params.get("status"));
        }
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),queryWrapper

        );

        return new PageUtils(page);
    }

}

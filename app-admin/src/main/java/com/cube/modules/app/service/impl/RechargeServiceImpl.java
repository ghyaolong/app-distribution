package com.cube.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cube.common.utils.MyStringUtils;
import com.cube.modules.app.entity.MemberEntity;
import com.cube.modules.app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.modules.app.dao.RechargeDao;
import com.cube.modules.app.entity.RechargeEntity;
import com.cube.modules.app.service.RechargeService;
import org.springframework.util.CollectionUtils;


@Service("rechargeService")
public class RechargeServiceImpl extends ServiceImpl<RechargeDao, RechargeEntity> implements RechargeService {

    @Autowired
    private MemberService memberService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<RechargeEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<RechargeEntity> lambda = queryWrapper.lambda();
        if(!MyStringUtils.isEmpty(params.get("orderId"))){
            lambda.eq(RechargeEntity::getOrderId,params.get("orderId"));
        }
        if(!MyStringUtils.isEmpty(params.get("userName"))){
            List<MemberEntity> entityList = memberService.list(new QueryWrapper<MemberEntity>().lambda().like(MemberEntity::getUserName, params.get("userName")));
            if(!CollectionUtils.isEmpty(entityList)){
                lambda.in(RechargeEntity::getMemberId,entityList.stream().map(MemberEntity::getId).collect(Collectors.toList()));
            }
        }
        if(!MyStringUtils.isEmpty(params.get("rechargeStartTime"))){
            lambda.ge(RechargeEntity::getRechargeTime,params.get("rechargeStartTime"));
        }
        if(!MyStringUtils.isEmpty(params.get("rechargeEndTime"))){
            lambda.le(RechargeEntity::getRechargeTime,params.get("rechargeEndTime"));
        }
        lambda.orderByDesc(RechargeEntity::getRechargeTime);
        IPage<RechargeEntity> page = this.page(
                new Query<RechargeEntity>().getPage(params),queryWrapper

        );

        return new PageUtils(page);
    }

}

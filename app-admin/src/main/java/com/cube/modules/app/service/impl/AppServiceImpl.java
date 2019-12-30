package com.cube.modules.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cube.common.utils.*;
import com.cube.modules.app.entity.MemberEntity;
import com.cube.modules.app.entity.PackageEntity;
import com.cube.modules.app.service.MemberService;
import com.cube.modules.app.service.PackageService;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cube.modules.app.dao.AppDao;
import com.cube.modules.app.entity.AppEntity;
import com.cube.modules.app.service.AppService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Service("appService")
public class AppServiceImpl extends ServiceImpl<AppDao, AppEntity> implements AppService {

    @Autowired
    private PathManagerUtils pathManagerUtils;

    @Autowired
    private PackageService packageService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<AppEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<AppEntity> lambda = queryWrapper.lambda();
        if(!MyStringUtils.isEmpty(params.get("name"))){
            lambda.like(AppEntity::getName,params.get("name").toString());
        }

        if(!MyStringUtils.isEmpty(params.get("startTime"))){
            lambda.ge(AppEntity::getCreateTime,params.get("startTime").toString());
        }

        if(!MyStringUtils.isEmpty(params.get("endTime"))){
            lambda.le(AppEntity::getCreateTime,params.get("endTime").toString());
        }

        if(!MyStringUtils.isEmpty(params.get("minDownloadCount"))){
            lambda.ge(AppEntity::getDownloadCount,params.get("minDownloadCount").toString());
        }

        if(!MyStringUtils.isEmpty(params.get("maxDownloadCount"))){
            lambda.le(AppEntity::getDownloadCount,params.get("maxDownloadCount").toString());
        }

        if(!MyStringUtils.isEmpty(params.get("userName"))){
            List<MemberEntity> memberEntityList = memberService.list(new QueryWrapper<MemberEntity>().lambda().like(MemberEntity::getUserName, params.get("userName").toString()));
            if(!Collections.isEmpty(memberEntityList)){
                List<String> collect = memberEntityList.stream().map(MemberEntity::getId).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(collect)){
                    lambda.in(AppEntity::getMemberId,memberEntityList.stream().map(MemberEntity::getId).collect(Collectors.toList()));
                }
            }
        }
        IPage<AppEntity> page = this.page(
                new Query<AppEntity>().getPage(params),queryWrapper
        );
        for (AppEntity record : page.getRecords()) {
            List<PackageEntity> list = packageService.list(new QueryWrapper<PackageEntity>().lambda().eq(PackageEntity::getAppId, record.getId()));
            pathManagerUtils.getBaseURL(false);
            record.setIconUrl(pathManagerUtils.getBaseURL(false)+record.getPlatform()+ File.separator+record.getBundleId()+File.separator+record.getCreateTime()+"/icon.png");
            record.setPackageList(list);
        }
        return new PageUtils(page);
    }


}

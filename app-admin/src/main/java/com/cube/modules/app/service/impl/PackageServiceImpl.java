package com.cube.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cube.common.utils.MyStringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.modules.app.dao.PackageDao;
import com.cube.modules.app.entity.PackageEntity;
import com.cube.modules.app.service.PackageService;


@Service("packageService")
public class PackageServiceImpl extends ServiceImpl<PackageDao, PackageEntity> implements PackageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PackageEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PackageEntity> lambda = queryWrapper.lambda();
        if(!MyStringUtils.isEmpty(params.get("appId"))){
            lambda.eq(PackageEntity::getAppId,params.get("appId").toString());
        }
        IPage<PackageEntity> page = this.page(
                new Query<PackageEntity>().getPage(params),queryWrapper
        );
        return new PageUtils(page);
    }

}

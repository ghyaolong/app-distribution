package com.cube.modules.app.service.impl;

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
        IPage<PackageEntity> page = this.page(
                new Query<PackageEntity>().getPage(params),
                new QueryWrapper<PackageEntity>()
        );

        return new PageUtils(page);
    }

}

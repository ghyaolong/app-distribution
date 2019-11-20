package com.cube.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.dao.AppDao;
import com.cube.entity.AppEntity;
import com.cube.service.AppService;


@Service("appService")
public class AppServiceImpl extends ServiceImpl<AppDao, AppEntity> implements AppService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AppEntity> page = this.page(
                new Query<AppEntity>().getPage(params),
                new QueryWrapper<AppEntity>()
        );

        return new PageUtils(page);
    }

}

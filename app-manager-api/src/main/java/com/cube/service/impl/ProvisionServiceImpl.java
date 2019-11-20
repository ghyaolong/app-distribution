package com.cube.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.dao.ProvisionDao;
import com.cube.entity.ProvisionEntity;
import com.cube.service.ProvisionService;


@Service("provisionService")
public class ProvisionServiceImpl extends ServiceImpl<ProvisionDao, ProvisionEntity> implements ProvisionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProvisionEntity> page = this.page(
                new Query<ProvisionEntity>().getPage(params),
                new QueryWrapper<ProvisionEntity>()
        );

        return new PageUtils(page);
    }

}

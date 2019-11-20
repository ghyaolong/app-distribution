package com.cube.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.dao.WxConfigDao;
import com.cube.entity.WxConfigEntity;
import com.cube.service.WxConfigService;


@Service("wxConfigService")
public class WxConfigServiceImpl extends ServiceImpl<WxConfigDao, WxConfigEntity> implements WxConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WxConfigEntity> page = this.page(
                new Query<WxConfigEntity>().getPage(params),
                new QueryWrapper<WxConfigEntity>()
        );

        return new PageUtils(page);
    }

}

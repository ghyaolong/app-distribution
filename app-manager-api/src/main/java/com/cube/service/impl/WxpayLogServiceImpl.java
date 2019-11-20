package com.cube.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.dao.WxpayLogDao;
import com.cube.entity.WxpayLogEntity;
import com.cube.service.WxpayLogService;


@Service("wxpayLogService")
public class WxpayLogServiceImpl extends ServiceImpl<WxpayLogDao, WxpayLogEntity> implements WxpayLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WxpayLogEntity> page = this.page(
                new Query<WxpayLogEntity>().getPage(params),
                new QueryWrapper<WxpayLogEntity>()
        );

        return new PageUtils(page);
    }

}

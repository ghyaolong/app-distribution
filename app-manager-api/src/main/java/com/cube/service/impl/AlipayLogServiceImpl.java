package com.cube.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.dao.AlipayLogDao;
import com.cube.entity.AlipayLogEntity;
import com.cube.service.AlipayLogService;


@Service("alipayLogService")
public class AlipayLogServiceImpl extends ServiceImpl<AlipayLogDao, AlipayLogEntity> implements AlipayLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AlipayLogEntity> page = this.page(
                new Query<AlipayLogEntity>().getPage(params),
                new QueryWrapper<AlipayLogEntity>()
        );

        return new PageUtils(page);
    }

}

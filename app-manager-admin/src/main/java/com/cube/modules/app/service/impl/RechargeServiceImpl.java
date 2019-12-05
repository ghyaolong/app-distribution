package com.cube.modules.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.modules.app.dao.RechargeDao;
import com.cube.modules.app.entity.RechargeEntity;
import com.cube.modules.app.service.RechargeService;


@Service("rechargeService")
public class RechargeServiceImpl extends ServiceImpl<RechargeDao, RechargeEntity> implements RechargeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RechargeEntity> page = this.page(
                new Query<RechargeEntity>().getPage(params),
                new QueryWrapper<RechargeEntity>()
        );

        return new PageUtils(page);
    }

}

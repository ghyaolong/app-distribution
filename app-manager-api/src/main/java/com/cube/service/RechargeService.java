package com.cube.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.entity.RechargeEntity;

import java.util.Map;

/**
 * 充值记录
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
public interface RechargeService extends IService<RechargeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


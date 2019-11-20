package com.cube.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.entity.RechargeLogEntity;

import java.util.Map;

/**
 * 充值流水
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:44:44
 */
public interface RechargeLogService extends IService<RechargeLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


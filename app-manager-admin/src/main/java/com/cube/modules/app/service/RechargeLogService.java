package com.cube.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.modules.app.entity.RechargeLogEntity;

import java.util.Map;

/**
 * 充值记录管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:12
 */
public interface RechargeLogService extends IService<RechargeLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


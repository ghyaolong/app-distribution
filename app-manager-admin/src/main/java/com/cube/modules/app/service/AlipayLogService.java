package com.cube.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.modules.app.entity.AlipayLogEntity;

import java.util.Map;

/**
 * 支付宝支付日志
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:12
 */
public interface AlipayLogService extends IService<AlipayLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


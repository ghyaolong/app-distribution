package com.cube.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.modules.app.entity.WxpayLogEntity;

import java.util.Map;

/**
 * 微信支付日志
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
public interface WxpayLogService extends IService<WxpayLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package com.cube.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.entity.WxpayLogEntity;

import java.util.Map;

/**
 * 微信支付日志
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
public interface WxpayLogService extends IService<WxpayLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


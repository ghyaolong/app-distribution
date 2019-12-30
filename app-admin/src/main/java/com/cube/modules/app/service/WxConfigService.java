package com.cube.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.modules.app.entity.WxConfigEntity;

import java.util.Map;

/**
 * 微信配置
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
public interface WxConfigService extends IService<WxConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


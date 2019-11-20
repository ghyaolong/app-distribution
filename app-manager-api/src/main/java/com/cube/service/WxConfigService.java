package com.cube.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.entity.WxConfigEntity;

import java.util.Map;

/**
 * 
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
public interface WxConfigService extends IService<WxConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


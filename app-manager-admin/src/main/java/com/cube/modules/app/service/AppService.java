package com.cube.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.modules.app.entity.AppEntity;

import java.util.Map;

/**
 * APP管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:12
 */
public interface AppService extends IService<AppEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


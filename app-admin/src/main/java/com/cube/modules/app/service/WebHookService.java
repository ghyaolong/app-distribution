package com.cube.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.modules.app.entity.WebHookEntity;

import java.util.Map;

/**
 * hook管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
public interface WebHookService extends IService<WebHookEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


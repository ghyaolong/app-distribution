package com.cube.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.modules.app.entity.ProvisionEntity;

import java.util.Map;

/**
 * IOS文件描述
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:12
 */
public interface ProvisionService extends IService<ProvisionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package com.cube.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.entity.ProvisionEntity;

import java.util.Map;

/**
 * 
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
public interface ProvisionService extends IService<ProvisionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


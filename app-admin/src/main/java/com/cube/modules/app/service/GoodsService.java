package com.cube.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.modules.app.entity.GoodsEntity;

import java.util.Map;

/**
 * 商品管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:43
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package com.cube.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cube.modules.app.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:43
 */
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {
	
}

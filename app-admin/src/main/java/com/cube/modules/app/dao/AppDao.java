package com.cube.modules.app.dao;

import com.cube.modules.app.entity.AppEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * APP管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@Mapper
public interface AppDao extends BaseMapper<AppEntity> {
	
}

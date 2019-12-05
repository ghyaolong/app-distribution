package com.cube.modules.app.dao;

import com.cube.modules.app.entity.PackageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * APP包管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:43
 */
@Mapper
public interface PackageDao extends BaseMapper<PackageEntity> {
	
}

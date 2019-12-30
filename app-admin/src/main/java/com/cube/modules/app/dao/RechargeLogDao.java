package com.cube.modules.app.dao;

import com.cube.modules.app.entity.RechargeLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 充值记录管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@Mapper
public interface RechargeLogDao extends BaseMapper<RechargeLogEntity> {
	
}

package com.cube.modules.app.dao;

import com.cube.modules.app.entity.AlipayLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付宝支付日志
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@Mapper
public interface AlipayLogDao extends BaseMapper<AlipayLogEntity> {
	
}

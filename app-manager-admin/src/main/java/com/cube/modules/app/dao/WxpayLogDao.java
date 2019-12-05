package com.cube.modules.app.dao;

import com.cube.modules.app.entity.WxpayLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信支付日志
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:12
 */
@Mapper
public interface WxpayLogDao extends BaseMapper<WxpayLogEntity> {
	
}

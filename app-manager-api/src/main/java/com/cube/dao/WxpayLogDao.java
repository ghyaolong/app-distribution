package com.cube.dao;

import com.cube.entity.WxpayLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信支付日志
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:03:16
 */
@Mapper
public interface WxpayLogDao extends BaseMapper<WxpayLogEntity> {
	
}

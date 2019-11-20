package com.cube.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 充值流水
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:44:44
 */
@Data
@TableName("da_recharge_log")
public class RechargeLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 充值步骤
	 */
	private String stepNumber;
	/**
	 * 充值详情描述
	 */
	private String payContent;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 存储数据
	 */
	private String payRecord;
	/**
	 * 
	 */
	private String memberId;

}

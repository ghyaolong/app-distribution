package com.cube.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 充值记录管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@Data
@TableName("da_recharge_log")
public class RechargeLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
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
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 存储数据
	 */
	private String payRecord;
	/**
	 * 会员id
	 */
	private String memberId;

}

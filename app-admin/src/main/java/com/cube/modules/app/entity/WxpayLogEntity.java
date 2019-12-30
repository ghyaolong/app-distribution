package com.cube.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 微信支付日志
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@Data
@TableName("da_wxpay_log")
public class WxpayLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 会员id
	 */
	private String memeberId;
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 业务结果
	 */
	private String resultCode;
	/**
	 * 状态码
	 */
	private String returnCode;
	/**
	 * 总金额（单位：分）
	 */
	private BigDecimal totalFee;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 微信支付订单号
	 */
	private String transactionId;
	/**
	 * 交易创建时间
	 */
	private Date gmtCreate;
	/**
	 * 交易付款时间
	 */
	private Date gmtPayment;
	/**
	 * 支付完成时间
	 */
	private Date timeEnd;
	/**
	 * 支付状态。0：充值中；1：交易成功；2：交易失败
	 */
	private String payStatus;
	/**
	 * 订单金额
	 */
	private BigDecimal orderMoney;
	/**
	 * 交易描述
	 */
	private String body;

}

package com.cube.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 支付宝支付日志
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
@Data
@TableName("da_alipay_log")
public class AlipayLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 通知时间
	 */
	private Date notifyTime;
	/**
	 * 通知类型
	 */
	private String notifyType;
	/**
	 * 通知校验ID
	 */
	private String notifyId;
	/**
	 * 商户网站唯一订单号
	 */
	private String outTradeNo;
	/**
	 * 交易标题
	 */
	private String subject;
	/**
	 * 支付宝交易号
	 */
	private String tradeNo;
	/**
	 * 交易状态
	 */
	private String tradeStatus;
	/**
	 * 卖家支付宝用户号
	 */
	private String sellerId;
	/**
	 * 卖家支付宝账号
	 */
	private String sellerEmail;
	/**
	 * 买家支付宝用户号
	 */
	private String buyerId;
	/**
	 * 买家支付宝账号
	 */
	private String buyerEmail;
	/**
	 * 交易金额
	 */
	private BigDecimal totalFee;
	/**
	 * 交易创建时间
	 */
	private Date gmtCreate;
	/**
	 * 交易付款时间
	 */
	private Date gmtPayment;
	/**
	 * 支付状态。0：充值中；1：交易成功；2：交易失败
	 */
	private String payStatus;
	/**
	 * 订单金额
	 */
	private BigDecimal orderMoney;

}

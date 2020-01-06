package com.cube.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 充值管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@Data
@TableName("da_recharge")
public class RechargeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 订单id/充值记录id
	 */
	private String orderId;
	/**
	 * 会员id
	 */
	private String memberId;

	/**
	 * 会员名称
	 */
	private transient  String userName;
	/**
	 * 充值金额
	 */
	private BigDecimal rechargeAmount;
	/**
	 * 充值时间
	 */
	private Date rechargeTime;
	/**
	 * 付款方式。1:微信；2：支付宝
	 */
	private String payType;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 交易流水号
	 */
	private String transactionId;
	/**
	 * 是否发送短信（0表示不发送，1表示发送）
	 */
	private String whetherSms;
	/**
	 * 充值状态。0：待支付；1：充值中；2：交易成功；3：交易失败；4：已退款；5：交易关闭
	 */
	private String rechargeStatus;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 购买的下载次数包
	 */
	private String goodsId;

	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品价格
	 */
	private BigDecimal goodsPrice;

}

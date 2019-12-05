package com.cube.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信配置
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:12
 */
@Data
@TableName("da_wx_config")
public class WxConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * appid
	 */
	private String appId;
	/**
	 * 商户id
	 */
	private String mchId;
	/**
	 * 同步回调地址
	 */
	private String returnUrl;
	/**
	 * 异步回调地址
	 */
	private String notifyUrl;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 0:正常   1：禁用
	 */
	private String status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 随机字符串
	 */
	private String nonceStr;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 应用对应的凭证
	 */
	private String appSecret;
	/**
	 * 应用对应的密钥
	 */
	private String appKey;
	/**
	 * 获取预支付id的接口URL
	 */
	private String gateUrl;
	/**
	 * 其它参数。json字符串
	 */
	private String extension;

}

package com.cube.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:43
 */
@Data
@TableName("da_goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 产品编码
	 */
	private String productId;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 下载次数
	 */
	private Integer downloadAmount;
	/**
	 * 次序
	 */
	private Integer seq;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 0:正常   1：下架
	 */
	private String status;
	/**
	 * 是否是推荐 0:否   1：是
	 */
	private String isDefault;

}

package com.cube.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
@Data
@TableName("da_app")
public class AppEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private String bundleId;
	/**
	 * 
	 */
	private Long createTime;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String platform;
	/**
	 * 
	 */
	private String shortCode;
	/**
	 * 
	 */
	private String currentId;
	/**
	 * 会员id
	 */
	private String memberId;
	/**
	 * 下载次数
	 */
	private Integer downloadCount;
	/**
	 * 0:正常   1：下架(禁止下载)
	 */
	private String status;
	/**
	 * 一般情况下，当status是1时，remark作为解说字段
	 */
	private String remark;

	public String ossUrl;
}

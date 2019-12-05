package com.cube.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * APP管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:12
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
	 * bundleId
	 */
	private String bundleId;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 平台类型
	 */
	private String platform;
	/**
	 * shortCode
	 */
	private String shortCode;
	/**
	 * 当前包Id
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

}

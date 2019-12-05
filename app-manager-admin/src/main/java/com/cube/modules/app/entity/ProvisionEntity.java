package com.cube.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * IOS文件描述
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:12
 */
@Data
@TableName("da_provision")
public class ProvisionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * UUID
	 */
	private String uuid;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 设备数量
	 */
	private Integer deviceCount;
	/**
	 * 设备
	 */
	private String devices;
	/**
	 * 过期时间
	 */
	private Date expirationDate;
	/**
	 * 是否事企业版本
	 */
	private Boolean isEnterprise;
	/**
	 * teamId
	 */
	private String teamId;
	/**
	 * team名称
	 */
	private String teamName;
	/**
	 * 类型
	 */
	private String type;

}

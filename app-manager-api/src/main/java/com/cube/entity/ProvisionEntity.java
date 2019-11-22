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
@TableName("da_provision")
public class ProvisionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private String uuid;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Integer deviceCount;
	/**
	 * 
	 */
	private String devices;
	/**
	 * 
	 */
	private Date expirationDate;
	/**
	 * 
	 */
	private Boolean isEnterprise;
	/**
	 * 
	 */
	private String teamId;
	/**
	 * 
	 */
	private String teamName;
	/**
	 * 
	 */
	private String type;

}

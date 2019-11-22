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
 * @date 2019-11-20 17:44:44
 */
@Data
@TableName("da_package")
public class PackageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 *
	 */
	private String buildVersion;
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
	private String extra;
	/**
	 *
	 */
	private String fileName;
	/**
	 *
	 */
	private String minVersion;
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
	private Long size;
	/**
	 *
	 */
	private String version;
	/**
	 *
	 */
	private String provisionId;
	/**
	 * 0:正常   1：已废弃(回退到上一个版本，当前版本废弃，只有一个版本的时候，无回退功能)
	 */
	private String status;
	/**
	 * 更新日志
	 */
	private String changeLog;

	/**
	 * appId
	 */
	private String appId;

}

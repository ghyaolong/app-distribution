package com.cube.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * APP包管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
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
	 * 构建版本
	 */
	private String buildVersion;
	/**
	 * BundleId
	 */
	private String bundleId;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 额外信息
	 */
	private String extra;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 最低版本
	 */
	private String minVersion;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 平台类型
	 */
	private String platform;
	/**
	 * 文件大小
	 */
	private Long size;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * provisionId
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
	/**
	 * oss下载地址
	 */
	private String ossUrl;

}

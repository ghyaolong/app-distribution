package com.cube.entity;

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
 * @date 2019-11-20 17:42:17
 */
@Data
@TableName("da_file")
public class FileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 包名
	 */
	private String packageId;
	/**
	 * 包名所在路径
	 */
	private String path;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 0:APP   1:jpg小图标   2：npg小图标
	 */
	private String type;
	/**
	 * oss所在路径
	 */
	private String ossPath;
	/**
	 * 0：阿里OSS平台      1：百度OSS     2：京东OSS
	 */
	private String ossType;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 会员id
	 */
	private String memberId;
	/**
	 * 所属app
	 */
	private String appId;

}

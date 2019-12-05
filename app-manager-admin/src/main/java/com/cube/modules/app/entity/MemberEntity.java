package com.cube.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:43
 */
@Data
@TableName("da_member")
public class MemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 手机
	 */
	private String tel;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 身份证号
	 */
	private String idNumber;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * qq号
	 */
	private String qq;
	/**
	 * 公司名称
	 */
	private String company;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 0：未审核   1：审核中  2：审核通过   3：审核驳回
	 */
	private String status;
	/**
	 * 冻结状态
	 */
	private String isForbidden;
	/**
	 * 剩余下载次数
	 */
	private Integer downloadCount;
	/**
	 * 正面照
	 */
	private String frontPhotoPath;
	/**
	 * 背面照
	 */
	private String backPhotoPath;
	/**
	 * 手持照
	 */
	private String handPhotoPath;
	/**
	 * 正面照oss路径
	 */
	private String frontPhotoOssPath;
	/**
	 * 背面照oss路径
	 */
	private String backPhotoOssPath;
	/**
	 * 手持照oss路径
	 */
	private String handPhotoOssPath;

}

package com.cube.vo;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class MemberVo {

    private String id;
    /**
     * 用户登录名称/即邮箱登录
     */
    //@Pattern(regexp = "^[0-9a-z]+\\w*@([0-9a-z]+\\.)+[0-9a-z]+$",message = "请输入合法的邮箱地址")
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
     *
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
     *
     */
    private Date createTime;
    /**
     * 0：未审核   1：审核中  2：审核通过   3：审核驳回
     */
    private String status;
    /**
     * 0:正常   1：已冻结
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

    private String token;

    /**
     * 验证码
     */
    private String validCode;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

}

package com.cube.vo;

import lombok.Data;

import java.util.Date;

@Data
public class PackageVo {

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
    private Date createTime;
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

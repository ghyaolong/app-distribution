package com.cube.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class PackageVo {

    private String id;
   
    private String buildVersion;
    
    private String bundleId;

    private Long createTime;

    private String extra;

    private String fileName;

    private String minVersion;

    private String name;

    private String platform;

    private Long size;

    private String version;

    private String provisionId;
    /**
     * 0:正常   1：已废弃(回退到上一个版本，当前版本废弃，只有一个版本的时候，无回退功能)
     */
    private String status;

    private String changeLog;

    private String appId;

    private ProvisionVo provisionVo;

    ////////////////////

    private String icon;

    private String shortCode;

    private String installPath;

    private String downloadURL;
    private String safeDownloadURL;
    private String iconURL;
    private String installURL;
    private String previewURL;
    private String displaySize;
    private String displayTime;

    private Boolean iOS;
    private String type;
    private List<String> devices;
    private int deviceCount;
    private String message;

    private String memberId;

    public String ossUrl;

}

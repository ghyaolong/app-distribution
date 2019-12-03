package com.cube.vo;

import lombok.Data;

import java.util.List;

@Data
public class AppVo {

    private String id;

    private String name;

    private String platform;

    private String bundleId;

    private String icon;

    private String version;

    private String buildVersion;

    private String minVersion;

    private String shortCode;

    private String installPath;

    private String currentId;

    public List<PackageVo> packageList;

    public PackageVo currentPackage;

    /**
     * 证书地址
     */
    public String caPath;

    public String basePath;
}

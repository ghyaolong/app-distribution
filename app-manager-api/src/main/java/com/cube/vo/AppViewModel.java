package com.cube.vo;

import lombok.Data;

import java.util.List;

@Data
public class AppViewModel {
    private String id;

    private String name;

    private String platform;

    private String bundleID;

    private String icon;

    private String version;

    private String buildVersion;

    private String minVersion;

    private String shortCode;

    private String installPath;

    private List<PackageVo> packageList;

    private PackageVo currentPackage;
}

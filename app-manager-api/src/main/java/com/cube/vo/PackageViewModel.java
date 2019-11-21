//package com.cube.vo;
//
//import com.alibaba.fastjson.JSON;
//import com.cube.entity.PackageEntity;
//import com.cube.utils.PathManager;
//import lombok.Data;
//import org.apache.commons.io.FileUtils;
//import org.springframework.util.StringUtils;
//
//import java.net.URLEncoder;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@Data
//public class PackageViewModel {
//
//    private String downloadURL;
//    private String safeDownloadURL;
//    private String iconURL;
//    private String installURL;
//    private String previewURL;
//    private String id;
//    private String version;
//    private String bundleID;
//    private String name;
//    private Date createTime;
//    private String buildVersion;
//    private String displaySize;
//    private String displayTime;
//    private boolean iOS;
//    private String type;
//    private List<String> devices;
//    private int deviceCount;
//    private String message;
//
//    public PackageViewModel(PackageEntity aPackage, PathManager pathManager) {
//        this.downloadURL = pathManager.getBaseURL(false) + "p/" + aPackage.getId();
//        this.safeDownloadURL = pathManager.getBaseURL(true) + "p/" + aPackage.getId();
//        this.iconURL = pathManager.getPackageResourceURL(aPackage, true) + "icon.png";
//        this.id = aPackage.getId();
//        this.version = aPackage.getVersion();
//        this.bundleID = aPackage.getBundleid();
//        this.name = aPackage.getName();
//        this.createTime = aPackage.getCreateTime();
//        this.buildVersion = aPackage.getBuildVersion();
//        this.displaySize = String.format("%.2f MB", aPackage.getSize() / (1.0F * FileUtils.ONE_MB));
//        String displayTime = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(this.createTime);
//        this.displayTime = displayTime;
//        if (aPackage.getPlatform().equals("ios")) {
//            this.iOS = true;
//            String url = pathManager.getBaseURL(true) + "m/" + aPackage.getId();
//            try {
//                this.installURL = "itms-services://?action=download-manifest&url=" + URLEncoder.encode(url, "utf-8");
//            } catch (Exception e){e.printStackTrace();}
//        } else if (aPackage.getPlatform().equals("android")) {
//            this.iOS = false;
//            this.installURL = pathManager.getPackageResourceURL(aPackage, false) + aPackage.getFileName();
//        }
//
//        this.previewURL = pathManager.getBaseURL(false) + "s/" + aPackage.getApp().getShortCode() + "?id=" + aPackage.getId();
//        if (this.isIOS()) {
//            if (aPackage.getProvision() == null) {
//                this.type = "内测版";
//            } else {
//                if (aPackage.getProvision().isEnterprise()) {
//                    this.type = "企业版";
//                } else  {
//                    if ("AdHoc".equalsIgnoreCase(aPackage.getProvision().getType())) {
//                        this.type = "内测版";
//                    } else {
//                        this.type = "商店版";
//                    }
//                    this.deviceCount = aPackage.getProvision().getDeviceCount();
//                    if (aPackage.getProvision().getDeviceCount() > 0) {
//                        this.devices = Arrays.asList(aPackage.getProvision().getDevices());
//                    }
//                }
//            }
//        } else {
//            this.type = "内测版";
//        }
//        String message = "";
//        if (StringUtils.hasLength(aPackage.getExtra())) {
//            Map<String, String> extra = (Map<String, String>) JSON.parse(aPackage.getExtra());
//            if (extra.containsKey("jobName")) {
//                message += " 任务名:" + extra.get("jobName");
//            }
//
//            if (extra.containsKey("buildNumber")) {
//                message += " 编号:#" + extra.get("buildNumber");
//            }
//        }
//        this.message = message;
//    }
//}

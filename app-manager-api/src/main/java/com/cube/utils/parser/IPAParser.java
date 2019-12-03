package com.cube.utils.parser;

import com.cube.utils.PNGConverter;
import com.cube.utils.PathManager;
import com.cube.utils.ZipUtils;
import com.cube.utils.ipa.Plist;
import com.cube.vo.PackageVo;
import com.cube.vo.ProvisionVo;
import com.dd.plist.NSDate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class IPAParser implements PackageParser {
    @Override
    public PackageVo parse(String filePath) {
        try {
            PackageVo aPackage = new PackageVo();
            // 解压 IPA 包
            String targetPath = ZipUtils.unzip(filePath);
            String appPath = appPath(targetPath);
            String infoPlistPath = appPath + File.separator + "Info.plist";
            infoPlistPath = infoPlistPath.replaceAll("//", "/");
            File infoPlistFile  = new File(infoPlistPath);
            // Plist 文件获取失败
            if (!infoPlistFile.exists()) return null;
            // 获取 infoPlist
            Plist infoPlist = Plist.parseWithFile(infoPlistFile);
            File ipaFile = new File(filePath);
            long currentTimeMillis = System.currentTimeMillis();

            aPackage.setSize(ipaFile.length());
            aPackage.setName(infoPlist.stringValueForPath("CFBundleDisplayName"));
            if (aPackage.getName() == null) {
                aPackage.setName(infoPlist.stringValueForPath("CFBundleName"));
            }
            aPackage.setVersion(infoPlist.stringValueForPath("CFBundleShortVersionString"));
            aPackage.setBuildVersion(infoPlist.stringValueForPath("CFBundleVersion"));
            aPackage.setBundleId(infoPlist.stringValueForPath("CFBundleIdentifier"));
            aPackage.setMinVersion(infoPlist.stringValueForPath("MinimumOSVersion"));
            aPackage.setCreateTime(currentTimeMillis);
            aPackage.setPlatform("ios");

            // 获取应用图标
            String iconName = infoPlist.stringValueForKeyPath("CFBundleIcons.CFBundlePrimaryIcon.CFBundleIconName");
            if (iconName == null) {
                iconName = infoPlist.stringValueForKeyPath("CFBundleIconFile");
            }
            String iconPath = appIcon(appPath, iconName);
            String iconTempPath = PathManager.getTempIconPath(aPackage);
            PNGConverter.convert(iconPath, iconTempPath);

            // 解析 Provision
            aPackage.setProvisionVo(getProvision(appPath));

            // 清除目录
            FileUtils.deleteDirectory(new File(targetPath));
            return aPackage;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*获取 APP 路径*/
    private static String appPath(String path) {
        try {
            String payloadPath = path + File.separator + "Payload";
            File payloadFile = new File(payloadPath);
            if (!payloadFile.exists()) return null;
            if (!payloadFile.isDirectory()) return null;
            File[] listFiles = payloadFile.listFiles();
            String appName = null;
            for (File file : listFiles) {
                if (file.getName().endsWith(".app")) {
                    appName = file.getName();
                    break;
                }
            }
            if (appName == null) return null;
            return payloadPath + File.separator + appName;
        } catch (Exception e) {

        }
        return null;
    }

    // 获取 APP 图标
    private static String appIcon(String appPath, String iconName) {
        List<String> iconNames = new ArrayList<>();
        File appFile = new File(appPath);
        File[] listFiles = appFile.listFiles();
        for (File file : listFiles) {
            String pattern = iconName + "[4,6]0x[4,6]0@[2,3]?x.png";
            boolean isMatch = Pattern.matches(pattern, file.getName());
            if (isMatch) {
                iconNames.add(file.getName());
            } else if (file.getName().equals(iconName)) {
                iconNames.add(file.getName());
            }
        }
        if (iconNames.size() > 0) {
            return appPath + File.separator + iconNames.get(iconNames.size() - 1);
        }
        return null;
    }

    private static ProvisionVo getProvision(String appPath) {
        ProvisionVo provision = new ProvisionVo();
        String profile = appPath + File.separator + "embedded.mobileprovision";
        try {
            boolean started = false;
            boolean ended = false;
            BufferedReader reader = new BufferedReader(new FileReader(profile));
            StringBuffer plist = new StringBuffer();
            String str = null;
            while ((str = reader.readLine()) != null) {
                if (str.contains("</plist>")) {
                    ended = true;
                    plist.append("</plist>").append("\n");
                } else if (started && !ended) {
                    plist.append(str).append("\n");
                } else  if (str.contains("<?xml")) {
                    started = true;
                    plist.append(str.substring(str.indexOf("<?xml"))).append("\n");
                }
            }
            reader.close();
            Plist provisionFile = Plist.parseWithString(plist.toString());
            provision.setIsEnterprise(provisionFile.boolValueForPath("ProvisionsAllDevices"));
            List<String> provisionedDevices = provisionFile.arrayValueForPath("ProvisionedDevices");
            String[] devices = new String[provisionedDevices.size()];
            devices = provisionedDevices.toArray(devices);
            provision.setDevices(StringUtils.join(provisionedDevices,","));
            provision.setDeviceCount(devices.length);
            provision.setTeamName(provisionFile.stringValueForPath("TeamName"));
            provision.setTeamId(provisionFile.arrayValueForPath("TeamIdentifier").get(0));
            provision.setCreateDate(((NSDate)provisionFile.valueForKeyPath("CreationDate")).getDate());
            provision.setExpirationDate(((NSDate)provisionFile.valueForKeyPath("ExpirationDate")).getDate());
            provision.setUuid(provisionFile.stringValueForPath("UUID"));
            provision.setType(provision.getDeviceCount() > 0 ? "AdHoc" : "Release");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provision;
    }
}

package com.cube.utils.parser;

import com.cube.utils.PathManager;
import com.cube.vo.PackageVo;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.IconFace;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Date;

public class APKParser implements PackageParser {
    @Override
    public PackageVo parse(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) return null;
            ApkFile apkFile = new ApkFile(file);
            long currentTimeMillis = System.currentTimeMillis();
            PackageVo aPackage = new PackageVo();
            aPackage.setSize(file.length());
            ApkMeta meta = apkFile.getApkMeta();
            aPackage.setName(meta.getName());
            String version = meta.getVersionName();
            String buildVersion = meta.getVersionCode() + "";
            if (version.length() < 1) {
                version = meta.getPlatformBuildVersionName();
            }
            if (buildVersion.length() < 1) {
                buildVersion = meta.getPlatformBuildVersionCode();
            }
            aPackage.setVersion(version);
            aPackage.setBuildVersion(buildVersion);
            aPackage.setBundleId(meta.getPackageName());
            aPackage.setMinVersion(meta.getMinSdkVersion());
            aPackage.setPlatform("android");
            aPackage.setCreateTime(currentTimeMillis);
            int iconCount = apkFile.getAllIcons().size();
            if (iconCount > 0) {
                IconFace icon = apkFile.getAllIcons().get(iconCount - 1);
                String iconPath = PathManager.getTempIconPath(aPackage);
                File iconFile = new File(iconPath);
                FileUtils.writeByteArrayToFile(iconFile, icon.getData());
            }
            apkFile.close();
            return aPackage;
        }catch (Exception e){

        }
        return null;
    }
}

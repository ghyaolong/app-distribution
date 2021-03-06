package com.cube.utils;

import com.cube.entity.PackageEntity;
import com.cube.vo.AppVo;
import com.cube.vo.PackageVo;
import org.apache.commons.io.FileUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.net.InetAddress;

@Component
public class PathManager {
    @Resource
    private Environment environment;
    private String httpsBaseURL;
    private String httpBaseURL;

    /**
     * 获取基础路径
     * @param isHttps
     * @return
     */
    public String getBaseURL(boolean isHttps) {
        if (isHttps) {
            if (httpsBaseURL != null) {
                return httpsBaseURL;
            }
        } else {
            if (httpBaseURL != null) {
                return httpBaseURL;
            }
        }

        try {
            // URL
            InetAddress address = InetAddress.getLocalHost();
            String domain=environment.getProperty("server.domain");
            if (domain == null) {
                domain = address.getHostAddress();
            }
            int httpPort = Integer.parseInt(environment.getProperty("server.http.port"));
            int httpsPort = Integer.parseInt(environment.getProperty("server.port"));
            int port = isHttps ? httpsPort : httpPort;
            String protocol = isHttps ? "https" : "http";
            String portString = ":" + port;
            if (port == 80 || port == 443) {
                portString = "";
            }

            String baseURL = protocol + "://" + domain + portString +environment.getProperty("server.servlet.context-path")+"/";

            //解决重复读配置文件
            if (isHttps) {
                httpsBaseURL = baseURL;
            } else {
                httpBaseURL = baseURL;
            }

            return baseURL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取包所在路径
     * @param aPackage
     * @param isHttps
     * @return
     */
    public String getPackageResourceURL(PackageVo aPackage, boolean isHttps) {
        String baseURL = getBaseURL(isHttps);
        String resourceURL = baseURL + aPackage.getPlatform() + "/" + aPackage.getBundleId() + "/" + aPackage.getCreateTime() + "/";
        return resourceURL;
    }

    /**
     * 获取头像URL
     * @return
     */
    public String getAvatarUrl(){
        return getBaseURL(false);
    }

    /**
     * 获取证书路径
     * @return
     */
    public String getCAPath() {
        return getBaseURL(false) + "crt/";
    }

    /**
     * 获取图标的临时路径
     * @param aPackage
     * @return
     */
    public static String getTempIconPath(PackageVo aPackage) {
        if (aPackage == null) return null;
        StringBuilder path = new StringBuilder();
        path.append(FileUtils.getTempDirectoryPath()).append(File.separator).append(aPackage.getPlatform());
        path.append(File.separator).append(aPackage.getBundleId());
        // 如果目录不存在，创建目录
        File dir = new File(path.toString());
        if (!dir.exists()) dir.mkdirs();
        path.append(File.separator).append(aPackage.getCreateTime()).append(".png");
        return path.toString();
    }

    /**
     * 获取上传路径
     * @return
     */
    public static String getUploadPath() {
        try {
            //获取跟目录
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");

            //如果上传目录为/static/upload/，则可以如下获取：
            File upload = new File(path.getAbsolutePath(),"static/upload/");
            if(!upload.exists()) upload.mkdirs();
            return upload.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取上传路径
     * @return
     */
    public static String getUploadPath(String dist) {
        try {
            //获取跟目录
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");

            //如果上传目录为/static/upload/，则可以如下获取：
            File upload = new File(path.getAbsolutePath(),"static/upload/"+dist);
            if(!upload.exists()) upload.mkdirs();
            return upload.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 APP 路径
     * @param app
     * @return
     */
    public static String getAppPath(AppVo app) {
        return getUploadPath() + File.separator + app.getPlatform() + File.separator + app.getBundleId() + File.separator;
    }

    /**
     * 获取包的完整路径
     * @param aPackage
     * @return
     */
    public static String getFullPath(PackageVo aPackage) {
        return getUploadPath() + File.separator + getRelativePath(aPackage);
    }

    /**
     * 获取包的相对路径
     * @param aPackage
     * @return
     */
    public static String getRelativePath(PackageVo aPackage) {
        if (aPackage == null) return null;
        StringBuilder path = new StringBuilder();
        path.append(aPackage.getPlatform()).append(File.separator);
        path.append(aPackage.getBundleId()).append(File.separator);
        path.append(aPackage.getCreateTime()).append(File.separator);
        return path.toString();
    }

    /**
     * 清除目录
     * @param path
     */
    public static void deleteDirectory(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            try {
                FileUtils.deleteDirectory(dir);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

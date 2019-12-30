package com.cube.common.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.net.InetAddress;

@Component
public class PathManagerUtils {
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

//    public String getAppIcon(){
//        return getBaseURL(false)+""
//    }


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

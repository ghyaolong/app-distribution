package com.cube.utils;

import com.cube.entity.PackageEntity;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

            String baseURL = protocol + "://" + domain + portString + "/";

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
    public String getPackageResourceURL(PackageEntity aPackage, boolean isHttps) {
        String baseURL = getBaseURL(isHttps);
        String resourceURL = baseURL + aPackage.getPlatform() + "/" + aPackage.getBundleId() + "/" + aPackage.getCreateTime() + "/";
        return resourceURL;
    }

}

package com.cube.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Resource
    private Environment environment;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源处理
        try {
            String prefix = ResourceUtils.FILE_URL_PREFIX ;
            String baseUpload = environment.getProperty("config.upload");
            registry.addResourceHandler("/android/**").addResourceLocations(prefix+ baseUpload+"android/");
            registry.addResourceHandler("/ios/**").addResourceLocations(prefix + baseUpload+"ios/");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

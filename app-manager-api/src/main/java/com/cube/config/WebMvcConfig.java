/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cube.config;

import com.cube.interceptor.AuthorizationInterceptor;
import com.cube.resolver.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * MVC配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private Environment environment;

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/cube/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            String path = ResourceUtils.getURL("").getPath();
            String prefix = ResourceUtils.FILE_URL_PREFIX + path;
            String debug = environment.getProperty("config.debug");
            if (debug !=null && debug.equals("debug")) {
                prefix = "classpath:/";
            }

            registry.addResourceHandler("/android/**").addResourceLocations(prefix+ "static/upload/android/");
            registry.addResourceHandler("/ios/**").addResourceLocations(prefix + "static/upload/ios/");
            registry.addResourceHandler("/crt/**").addResourceLocations(prefix + "static/crt/");
            registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
            registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
            registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
            registry.addResourceHandler("/certificate/**").addResourceLocations("classpath:/static/upload/certificate/");
            //super.addResourceHandlers(registry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/" ).setViewName( "forward:/apps" );
    }
}
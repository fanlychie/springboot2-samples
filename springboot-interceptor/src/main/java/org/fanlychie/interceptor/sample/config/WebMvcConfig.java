package org.fanlychie.interceptor.sample.config;

import org.fanlychie.interceptor.sample.interceptor.LoggingInterceptor;
import org.fanlychie.interceptor.sample.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by fanlychie on 2019/6/4.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志拦截器拦截所有请求
        registry.addInterceptor(new LoggingInterceptor())
                .addPathPatterns("/*");
        // 登录拦截器拦截所有请求, 除了 "/index", "/login"
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/*")
                .excludePathPatterns("/index", "/login");
    }

}
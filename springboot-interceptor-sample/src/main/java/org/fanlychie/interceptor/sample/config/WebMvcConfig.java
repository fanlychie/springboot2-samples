package org.fanlychie.interceptor.sample.config;

import org.fanlychie.interceptor.sample.interceptor.LoggingInterceptor;
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
        // 拦截所有的请求, 如需拦截指定地址采用.addPathPatterns()
        registry.addInterceptor(new LoggingInterceptor());
    }

}
package com.spider_net.config;

import com.spider_net.config.Intercept.LoginIntercept;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginIntercept())
                .addPathPatterns("/manage/**")
                .excludePathPatterns("/manage/login")
                .excludePathPatterns("/manage/captcha")
                .excludePathPatterns("/assets/**");
    }
}

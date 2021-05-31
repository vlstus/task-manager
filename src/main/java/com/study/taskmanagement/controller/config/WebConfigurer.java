package com.study.taskmanagement.controller.config;

import com.study.taskmanagement.controller.interceptor.UserAuthenticationAddingHandleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer
        implements WebMvcConfigurer {

    @Autowired
    UserAuthenticationAddingHandleInterceptor authenticationAddingHandleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationAddingHandleInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}

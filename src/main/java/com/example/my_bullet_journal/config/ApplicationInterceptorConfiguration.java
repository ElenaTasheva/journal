package com.example.my_bullet_journal.config;

import com.example.my_bullet_journal.web.interceptors.ErrorPageInterceptor;
import com.example.my_bullet_journal.web.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationInterceptorConfiguration implements WebMvcConfigurer {

    private final TitleInterceptor interceptor;
    private final ErrorPageInterceptor errorPageInterceptor;

    @Autowired
    public ApplicationInterceptorConfiguration(TitleInterceptor interceptor, ErrorPageInterceptor errorPageInterceptor) {
        this.interceptor = interceptor;
        this.errorPageInterceptor = errorPageInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.interceptor);
        registry.addInterceptor(this.errorPageInterceptor).addPathPatterns("/error/**");

    }
}


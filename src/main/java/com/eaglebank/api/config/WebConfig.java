package com.eaglebank.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

import com.eaglebank.api.metrics.RequestMetricsInterceptor;

/**
 * Configures web-related settings for the application.
 * This includes registering interceptors for request metrics.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RequestMetricsInterceptor requestMetricsInterceptor;

    /**
     * Register the RequestMetricsInterceptor to track request metrics.
     * This interceptor will measure the duration of each request and log it.
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(requestMetricsInterceptor);
    }

}

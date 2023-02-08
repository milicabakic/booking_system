package com.griddynamics.lidlbooking.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
@ConditionalOnProperty(prefix = "interceptor", name = "enabled", havingValue = "true")
public class InterceptorConfig implements WebMvcConfigurer {

    private final LoggingInterceptor interceptorLogging;
    private final AddIdInterceptor interceptorId;


    @Value("${interceptor.log_routes}")
    private List<String> logPaths;

    @Value("${interceptor.dont_log_routes}")
    private List<String> dontLogPaths;

    @Autowired
    public InterceptorConfig(final LoggingInterceptor interceptorLogging, final AddIdInterceptor interceptorId) {
        this.interceptorLogging = interceptorLogging;
        this.interceptorId = interceptorId;
    }


    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(interceptorId).addPathPatterns(logPaths).excludePathPatterns(dontLogPaths);
        registry.addInterceptor(interceptorLogging).addPathPatterns(logPaths).excludePathPatterns(dontLogPaths);
    }

}

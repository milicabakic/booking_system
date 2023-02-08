package com.griddynamics.lidlbooking.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    private final RequestLoggingStrategy requestLoggingStrategy;

    private final ResponseLoggingStrategy responseLoggingStrategy;

    @Autowired
    public LoggingInterceptor(final RequestLoggingStrategy requestLoggingStrategy,
                              final ResponseLoggingStrategy responseLoggingStrategy) {
        this.requestLoggingStrategy = requestLoggingStrategy;
        this.responseLoggingStrategy = responseLoggingStrategy;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {

        logger.info(requestLoggingStrategy.printRequest(request));

        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, @Nullable final Exception ex) {

        logger.info(responseLoggingStrategy.printResponse(response));

    }


}

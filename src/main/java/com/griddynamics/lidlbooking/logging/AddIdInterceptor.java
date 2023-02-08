package com.griddynamics.lidlbooking.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class AddIdInterceptor implements HandlerInterceptor {

    @Value("${response.headerName}")
    private String headerName;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {

        request.setAttribute("id", UUID.randomUUID());

        response.setHeader(headerName, request.getAttribute("id").toString());

        return true;
    }


}

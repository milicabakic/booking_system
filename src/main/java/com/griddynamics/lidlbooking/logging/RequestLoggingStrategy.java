package com.griddynamics.lidlbooking.logging;

import javax.servlet.http.HttpServletRequest;

public interface RequestLoggingStrategy {

    String printRequest(HttpServletRequest request);
}

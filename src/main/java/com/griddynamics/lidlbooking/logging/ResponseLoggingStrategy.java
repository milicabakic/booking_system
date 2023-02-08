package com.griddynamics.lidlbooking.logging;

import javax.servlet.http.HttpServletResponse;

public interface ResponseLoggingStrategy {

    String printResponse(HttpServletResponse response);
}

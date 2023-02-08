package com.griddynamics.lidlbooking.commons;

public class SecurityConstants {

    public static final String SECRET = "My JWT secret key";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final int TOKEN_EXPIRATION = 1000 * 60 * 60 * 10;
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_CLAIMS_KEY = "roles";
    public static final String ACCESS_TOKEN = "Access token";
    public static final String REFRESH_TOKEN = "Refresh token";

    public static final String AUTHENTICATION_FAILED = "Authentication failed";
    public static final String AUTHENTICATION_SUCCESS = "Successful Authentication";
    public static final String AUTHENTICATION_DENIED = "Authentication denied";
}

package com.ppmtool.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "/h2-console/**";
    public static final String SECRET = "SecretKeyForJWTs";
    public static final String TOKEN_PREFIX = "Bearer "; // leave a space here
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION_TIME = 3000_000;
}

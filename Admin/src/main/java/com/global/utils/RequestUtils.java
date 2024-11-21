package com.global.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static String extractUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public static String extractCommand(String uri, String contextPath) {
        return uri.substring(contextPath.length() + 1);
    }
}

package com.global.utils;

import java.sql.Date;

public class RequestParameterUtils {

    /**
     * 문자열을 Integer로 변환하는 유틸리티 메서드.
     * @param value 변환할 문자열 값
     * @return 변환된 Integer 값 또는 null
     */
    public static Integer parseInteger(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 문자열을 Integer로 변환하는 유틸리티 메서드, 기본 값을 제공.
     * @param value 변환할 문자열 값
     * @param defaultValue 기본값
     * @return 변환된 Integer 값 또는 기본값
     */
    public static Integer parseInteger(String value, int defaultValue) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 문자열을 Date로 변환하는 유틸리티 메서드.
     * @param value 변환할 문자열 값
     * @return 변환된 Date 값 또는 null
     */
    public static Date parseDate(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Date.valueOf(value) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 문자열을 String으로 변환하는 유틸리티 메서드, 기본 값을 제공.
     * @param value 변환할 문자열 값
     * @param defaultValue 기본값
     * @return 변환된 String 값 또는 기본값
     */
    public static String parseString(String value, String defaultValue) {
        try {
            return (value != null && !value.isEmpty()) ? String.valueOf(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 문자열을 Boolean으로 변환하는 유틸리티 메서드.
     * @param value 변환할 문자열 값
     * @return 변환된 Boolean 값 또는 null
     */
    public static Boolean parseBoolean(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Boolean.parseBoolean(value);
    }
}

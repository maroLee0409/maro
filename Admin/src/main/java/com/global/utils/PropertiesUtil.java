package com.global.utils;

import javax.servlet.ServletException;
import java.util.Properties;

/**
 * Properties 유틸리티 클래스.
 * <p>
 * Properties 객체의 상태를 검사하고 예외를 던지는 메서드를 제공합니다.
 * </p>
 */
public class PropertiesUtil {

    /**
     * Properties 객체가 초기화되지 않았을 때 예외를 던집니다.
     * 
     * @param prop 검사할 Properties 객체
     * @throws ServletException Properties 객체가 null일 경우 발생합니다.
     */
    public static void ensurePropertiesInitialized(Properties prop) throws ServletException {
        if (prop == null) {
            throw new ServletException("Properties not initialized");
        }
    }
}
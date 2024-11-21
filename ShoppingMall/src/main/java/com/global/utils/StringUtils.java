package com.global.utils;

public class StringUtils {
    public static String stripHtml(String input) {
        if (input == null) {
            return null;
        }
        // HTML 태그 제거 후 문자열 반환
        return String.valueOf(input.replaceAll("<[^>]*>", ""));
    }

    public static String truncateWithEllipsis(String input, int length) {
        if (input == null) {
            return null;
        }
        // HTML 태그 제거
        String strippedInput = stripHtml(input);

        // 글자 수가 length를 초과하면 자르고 "..." 추가
        if (strippedInput.length() > length) {
            return strippedInput.substring(0, length) + "...";
        } else {
            return strippedInput;
        }
    }
}

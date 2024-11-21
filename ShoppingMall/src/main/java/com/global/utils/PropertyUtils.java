package com.global.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    /**
     * 지정된 파일 이름으로 Properties 파일을 동적으로 로드하는 메서드
     *
     * @param fileName 파일 경로
     * @return 로드된 Properties 객체
     * @throws IOException 파일 읽기 오류 발생 시
     */
    public static Properties loadProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        String path = PropertyUtils.class.getResource(fileName).getPath();
        try (FileReader reader = new FileReader(path)) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return properties;
    }
}

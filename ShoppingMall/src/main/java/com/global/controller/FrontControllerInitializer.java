package com.global.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FrontControllerInitializer {

    private Properties properties = new Properties();

    public FrontControllerInitializer(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
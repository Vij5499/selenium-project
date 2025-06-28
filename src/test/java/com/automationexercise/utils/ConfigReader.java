package com.automationexercise.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private final Properties props = new Properties();

    public ConfigReader(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config: " + e);
        }
    }

    public String getBaseUrl()      { return props.getProperty("baseUrl"); }
    public String getValidEmail()   { return props.getProperty("validEmail"); }
    public String getValidPassword(){ return props.getProperty("validPassword"); }
}

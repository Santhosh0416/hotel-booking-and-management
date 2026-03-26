package com.hotel.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration utility class to load and manage application properties
 */
public class ConfigUtil {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
            properties = new Properties();
        }
    }

    /**
     * Get a property value by key
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get a property value with a default fallback
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get a property value as integer
     */
    public static int getIntProperty(String key, int defaultValue) {
        try {
            String value = properties.getProperty(key);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Get application name
     */
    public static String getAppName() {
        return getProperty("app.name", "Hotel Booking Management System");
    }

    /**
     * Get application version
     */
    public static String getAppVersion() {
        return getProperty("app.version", "1.0.0");
    }
}

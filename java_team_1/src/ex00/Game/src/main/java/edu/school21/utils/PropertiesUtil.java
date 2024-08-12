package edu.school21.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
    public static final String DEVELOPMENT_PROFILE = "development";
    private static final Properties PROPERTIES = new Properties();
    private static final String DEVELOPMENT_PROPERTIES_PATH = "application-dev.properties";
    private static final String PRODUCTION_PROPERTIES_PATH = "application-production.properties";

    private PropertiesUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void loadProperties(String profile) {
        String path = DEVELOPMENT_PROFILE.equalsIgnoreCase(profile)
                ? DEVELOPMENT_PROPERTIES_PATH
                : PRODUCTION_PROPERTIES_PATH;

        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalStateException("Properties file not found: " + path);
            }

            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Error loading properties file: " + path, e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}

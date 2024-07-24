package com.csg.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private PropertiesUtils() { }

    /**
     * Loads properties file.
     *
     * @param propertiesFileName The name of the properties file to load.
     * @return A Properties object containing the loaded properties.
     * @throws IOException If an I/O error occurs.
     */
    public static Properties loadProperties(String propertiesFileName) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (input == null) {
                throw new IOException("Resource not found: " + propertiesFileName);
            }
            properties.load(input);
        }
        return properties;
    }
}

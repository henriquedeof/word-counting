package com.csg.utilities;

import com.csg.constants.WordCountingConstant;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesUtilsTest {

    private static final String PROPERTIES_FILE = "wordCounting-test.properties";

    @Test
    void testLoadProperties_shouldLoadSuccessfully() throws IOException {
        Properties properties = PropertiesUtils.loadProperties(PROPERTIES_FILE);
        assertNotNull(properties);
        assertFalse(properties.isEmpty());
        assertTrue(properties.containsKey(WordCountingConstant.WORD_LENGTH_MINIMUM));
        assertTrue(properties.containsKey(WordCountingConstant.STARTING_CHARACTER_SEARCH));
    }

    @Test
    void testLoadPropertiesFileNotFound_shouldThrowIOExceptionWhenPropertiesDoesNotExist() {
        assertThrows(IOException.class, () -> PropertiesUtils.loadProperties("nonexistent.properties"));
    }
}
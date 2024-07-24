package com.csg.processor.impl;

import com.csg.model.ProcessingResult;
import com.csg.utilities.PropertiesUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class WordProcessorByFileTest {
    private static final String PROPERTIES_FILE = "wordCounting-test.properties";

    private WordProcessorByFile wordProcessor;
    private Properties properties;

    @BeforeEach
    void setUp() throws IOException {
        properties = PropertiesUtils.loadProperties(PROPERTIES_FILE);
        wordProcessor = new WordProcessorByFile(properties);
    }

    @Test
    void testProcess_shouldProcessSuccessfully(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "apple banana apricot Magnificent mango");

        ProcessingResult result = wordProcessor.process(file.toString());
        assertEquals(2, result.getWordCount());
        assertEquals(3, result.getLongWords().size());
        assertTrue(result.getLongWords().contains("banana"));
        assertTrue(result.getLongWords().contains("apricot"));
        assertTrue(result.getLongWords().contains("Magnificent"));
    }

    @Test
    void testProcess_shouldThrowIOExceptionWhenFileDoesNotExist() {
        assertThrows(IOException.class, () -> wordProcessor.process("nonexistent.txt"));
    }

    @Test
    void testProcess_shouldThrowIOExceptionWhenFileIsDirectory(@TempDir Path tempDir) {
        assertThrows(IOException.class, () -> wordProcessor.process(tempDir.toString()));
    }

    @Test
    void testProcess_shouldThrowIOExceptionFileIsEmpty(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.createFile(file);
        assertThrows(IOException.class, () -> wordProcessor.process(file.toString()));
    }
}
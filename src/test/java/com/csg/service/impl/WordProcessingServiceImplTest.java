package com.csg.service.impl;

import com.csg.constants.WordCountingConstant;
import com.csg.model.ProcessingResult;
import com.csg.processor.WordProcessor;
import com.csg.service.WordProcessingService;
import com.csg.utilities.PropertiesUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class WordProcessingServiceImplTest {
    private static final String PROPERTIES_FILE = "wordCounting-test.properties";

    private WordProcessor wordProcessorMock;
    private Properties properties;
    private WordProcessingService service;

    @BeforeEach
    void setUp() throws IOException {
        wordProcessorMock = mock(WordProcessor.class);
        properties = PropertiesUtils.loadProperties(PROPERTIES_FILE);
        service = new WordProcessingServiceImpl(wordProcessorMock, properties);
    }

    @Test
    void testProcess_shouldProcessSuccessfully() throws IOException {
        when(wordProcessorMock.process(anyString())).thenReturn(new ProcessingResult(2, List.of("VeryLongWord")));

        ProcessingResult result = assertDoesNotThrow(() -> service.process("filePath"));
        assertNotNull(result);
        assertEquals(2, result.getWordCount());
        assertEquals(1, result.getLongWords().size());
        assertEquals("VeryLongWord", result.getLongWords().get(0));
    }

    @Test
    void testProcess_shouldThrowIllegalArgumentExceptionWhenStartingCharacterIsNull() {
        properties.remove(WordCountingConstant.STARTING_CHARACTER_SEARCH);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.process("filePath"));
        assertNotNull(exception);
    }

    @Test
    void testProcess_shouldThrowIllegalArgumentExceptionWhenStartingCharacterLengthDifferentFromOne() {
        properties.setProperty(WordCountingConstant.STARTING_CHARACTER_SEARCH, "ab");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.process("filePath"));
        assertNotNull(exception);
    }

    @Test
    void testProcess_shouldIllegalArgumentExceptionWhenInvalidWordLengthMinimum() {
        properties.setProperty(WordCountingConstant.WORD_LENGTH_MINIMUM, "notAnInteger");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.process("filePath"));
        assertNotNull(exception);
    }

    @Test
    void testProcess_shouldThrowIOException() throws IOException {
        when(wordProcessorMock.process(anyString())).thenThrow(new IOException("Test exception"));
        assertThrows(IOException.class, () -> service.process("filePath"));
    }
}
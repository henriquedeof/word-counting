package com.csg;

import com.csg.constants.WordCountingConstant;
import com.csg.model.ProcessingResult;
import com.csg.service.WordProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProcessorMainClassTest {
    private WordProcessingService wordProcessingServiceMock;
    private Properties propertiesMock;
    private ProcessorMainClass processorMainClass;

    @BeforeEach
    void setUp() {
        wordProcessingServiceMock = Mockito.mock(WordProcessingService.class);
        propertiesMock = Mockito.mock(Properties.class);
        processorMainClass = new ProcessorMainClass(wordProcessingServiceMock, propertiesMock);
    }

    @Test
    void testStartProcessing_shouldExecuteSuccessfully() throws IOException {
        when(wordProcessingServiceMock.process(anyString())).thenReturn(new ProcessingResult(10, null));
        when(propertiesMock.getProperty(WordCountingConstant.STARTING_CHARACTER_SEARCH)).thenReturn("m");
        when(propertiesMock.getProperty(WordCountingConstant.WORD_LENGTH_MINIMUM)).thenReturn("6");
        processorMainClass.startProcessing("validSource.txt");
        verify(wordProcessingServiceMock).process("validSource.txt");
    }

    @Test
    void testStartProcessing_shouldThrowIOException() throws IOException {
        when(wordProcessingServiceMock.process(anyString())).thenThrow(new IOException("Test exception"));
        IOException exception = assertThrows(IOException.class, () -> processorMainClass.startProcessing("validSource.txt"));
        assertNotNull(exception);
        verify(wordProcessingServiceMock, times(1)).process("validSource.txt");
    }
}
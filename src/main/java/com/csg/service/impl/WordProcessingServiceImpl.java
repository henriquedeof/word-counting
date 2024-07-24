package com.csg.service.impl;

import com.csg.constants.WordCountingConstant;
import com.csg.model.ProcessingResult;
import com.csg.processor.WordProcessor;
import com.csg.service.WordProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class WordProcessingServiceImpl implements WordProcessingService {
    private static final Logger LOG = LoggerFactory.getLogger(WordProcessingServiceImpl.class);

    private final WordProcessor wordProcessor;
    private final Properties properties;

    public WordProcessingServiceImpl(WordProcessor wordProcessor, Properties properties) {
        this.wordProcessor = wordProcessor;
        this.properties = properties;
    }

    /**
     * @see WordProcessingService#process(String)
     */
    @Override
    public ProcessingResult process(String source) throws IOException {
        LOG.debug("Validating properties fields");
        validatePropertiesFields(properties);

        LOG.info("Starting word processing");
        return wordProcessor.process(source);
    }

    private void validatePropertiesFields(Properties properties) {
        String startingCharacter = properties.getProperty(WordCountingConstant.STARTING_CHARACTER_SEARCH);
        String wordLengthMinimum = properties.getProperty(WordCountingConstant.WORD_LENGTH_MINIMUM);

        if (startingCharacter == null || startingCharacter.length() != 1) {
            String message = String.format("Invalid or missing '%s' property.", WordCountingConstant.STARTING_CHARACTER_SEARCH);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }

        try {
            Integer.parseInt(wordLengthMinimum);
        } catch (NumberFormatException e) {
            String message = String.format("Invalid or missing '%s' property.", WordCountingConstant.WORD_LENGTH_MINIMUM);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}

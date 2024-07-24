package com.csg;

import com.csg.constants.WordCountingConstant;
import com.csg.model.ProcessingResult;
import com.csg.processor.WordProcessor;
import com.csg.processor.impl.WordProcessorByFile;
import com.csg.service.WordProcessingService;
import com.csg.service.impl.WordProcessingServiceImpl;
import com.csg.utilities.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class ProcessorMainClass {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessorMainClass.class);

    private final WordProcessingService service;
    private final Properties properties;

    public ProcessorMainClass(WordProcessingService service, Properties properties) {
        this.service = service;
        this.properties = properties;
    }

    public void startProcessing(String source) throws IOException {
        ProcessingResult result = service.process(source);
        String characterSearch = properties.getProperty(WordCountingConstant.STARTING_CHARACTER_SEARCH);
        String minimumLength = properties.getProperty(WordCountingConstant.WORD_LENGTH_MINIMUM);

        LOG.info("Number of words starting with '{}' or '{}': {}", characterSearch.toLowerCase(), characterSearch.toUpperCase(), result.getWordCount());
        LOG.info("Words with at least {} characters: {}", minimumLength, result.getLongWords());
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            LOG.error("Please provide the following parameters: ");
            LOG.error("1) Source  |  2) Properties file");
            return;
        }

        try {
            String filePath = args[0];
            LOG.debug("Loading properties file: {}", args[1]);
            Properties properties = PropertiesUtils.loadProperties(args[1]);

            WordProcessor wordProcessor = new WordProcessorByFile(properties);
            WordProcessingService service = new WordProcessingServiceImpl(wordProcessor, properties);
            ProcessorMainClass processorMainClass = new ProcessorMainClass(service, properties);
            processorMainClass.startProcessing(filePath);
        } catch (IOException e) {
            LOG.error("Error processing file: {}", e.getMessage(), e);
        }
    }
}

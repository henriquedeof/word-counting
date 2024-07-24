package com.csg.processor.impl;

import com.csg.constants.WordCountingConstant;
import com.csg.model.ProcessingResult;
import com.csg.processor.WordProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * WordProcessor class for processing text files and applying business rules.
 */
public class WordProcessorByFile implements WordProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(WordProcessorByFile.class);

    private final Properties properties;

    public WordProcessorByFile(Properties properties) {
        this.properties = properties;
    }

    /**
     * @see WordProcessor#process(String)
     */
    public ProcessingResult process(String source) throws IOException {
        validateFile(source);
        LOG.info("Processing file: {}", source);
        List<String> words = readWordsFromFile(source);
        int wordCount = countWordsStartingWithCharacter(words);
        List<String> longWords = filterLongWords(words);

        return new ProcessingResult(wordCount, longWords);
    }

    /**
     * Reads words from a file and returns them as a list.
     *
     * @param filePath The path to the file to read.
     * @return A list of words read from the file.
     * @throws IOException If an I/O error occurs.
     */
    private List<String> readWordsFromFile(String filePath) throws IOException {
        LOG.debug("Reading words from file: {}", filePath);
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .map(String::trim)
                    .toList();
        }
    }

    /**
     * Counts the number of words in a list that start with a specific character in the properties file.
     *
     * @param words The list of words to count.
     * @return The number of words that start with the specified character.
     */
    private int countWordsStartingWithCharacter(List<String> words) {
        String startingCharacter = properties.getProperty(WordCountingConstant.STARTING_CHARACTER_SEARCH);
        LOG.debug("Counting words starting with a specific character ({})", startingCharacter);
        return (int) words.stream()
                .filter(word -> word.toLowerCase().startsWith(startingCharacter.toLowerCase()))
                .count();
    }

    /**
     * Filters a list of words based on the minimum word length specified in the properties file.
     *
     * @param words The list of words to filter.
     * @return A list of words that meet the minimum length requirement.
     */
    private List<String> filterLongWords(List<String> words) {
        Integer minimumLength = Integer.parseInt(properties.getProperty(WordCountingConstant.WORD_LENGTH_MINIMUM));
        LOG.debug("Filtering words by minimum length ({})", minimumLength);
        return words.stream()
                .filter(word -> word.length() >= minimumLength)
                .toList();
    }

    private void validateFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || file.length() == 0) {
            throw new IOException("File does not exist, is not a file, or is empty: " + filePath);
        }
    }
}
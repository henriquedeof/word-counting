package com.csg.model;

import java.util.List;

/**
 * ProcessingResult class to hold the results of word processing.
 */
public class ProcessingResult {
    private final int wordCount;
    private final List<String> longWords;

    public ProcessingResult(int wordCount, List<String> longWords) {
        this.wordCount = wordCount;
        this.longWords = longWords;
    }

    public int getWordCount() {
        return wordCount;
    }

    public List<String> getLongWords() {
        return longWords;
    }
}
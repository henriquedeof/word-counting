package com.csg.service;

import com.csg.model.ProcessingResult;

import java.io.IOException;

public interface WordProcessingService {

    /**
     * Processes a source and applies the specified business rules.
     *
     * @param source that needs to be processed.
     * @return A ProcessingResult object containing the results.
     * @throws IOException If an I/O error occurs.
     */
    ProcessingResult process(String source) throws IOException;
}

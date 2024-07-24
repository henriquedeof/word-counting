package com.csg.processor;

import com.csg.model.ProcessingResult;

import java.io.IOException;

public interface WordProcessor {

    /**
     * Processes a file and applies the specified business rules.
     *
     * @param source that needs to be processed.
     * @return A ProcessingResult object containing the results.
     * @throws IOException If an I/O error occurs.
     */
    ProcessingResult process(String source) throws IOException;
}

package org.brain.jobscrapping.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class CSVNotExportedException extends ServiceException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ErrorType errorType = ErrorType.PROCESSING_ERROR;
    private static final String DEFAULT_MESSAGE = "Folder is not created!";

    public CSVNotExportedException() {
        this(DEFAULT_MESSAGE);
    }

    public CSVNotExportedException(String message) {
        super(message);
    }
    public CSVNotExportedException(String message, Exception e) {
        super(message, e);
    }
}

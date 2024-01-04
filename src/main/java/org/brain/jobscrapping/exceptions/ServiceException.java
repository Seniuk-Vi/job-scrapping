package org.brain.jobscrapping.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorType errorType = ErrorType.PROCESSING_ERROR;

    ServiceException(String message) {
        super(message);
    }
    ServiceException(String message, Exception e) {
        super(message, e);
    }
    ServiceException() {
        this("Service exception!");
    }

}

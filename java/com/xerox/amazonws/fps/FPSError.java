package com.xerox.amazonws.fps;

import com.xerox.amazonws.common.AWSError;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class FPSError extends AWSError {
    private final boolean retriable;

    public FPSError(ErrorType type, String code, String message, boolean retriable) {
        super(type, code, message);
        this.retriable = retriable;
    }

    public boolean isRetriable() {
        return retriable;
    }
}

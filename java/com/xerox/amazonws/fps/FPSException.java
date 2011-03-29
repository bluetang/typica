package com.xerox.amazonws.fps;

import com.xerox.amazonws.fps.FPSError;
import com.xerox.amazonws.common.AWSException;
import com.xerox.amazonws.common.AWSError.ErrorType;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Collections;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class FPSException extends AWSException {
    public FPSException(String s) {
        super(s);
    }

    public FPSException(AWSException cause) {
        super(cause);
    }

    public FPSException(String s, Throwable cause) {
        super(s, cause);
    }

    public FPSException(String requestID, String status, String errorMessage) {
	super(errorMessage, requestID, Collections.singletonList(new FPSError(ErrorType.SENDER, status, errorMessage, false)));
    }

    public FPSException(String requestID, List<FPSError> errors) {
        super(String.format("Invalid request %s. %s", requestID, concatenateErrors(errors)), requestID, errors);
    }

    @SuppressWarnings("unchecked")
    public List<FPSError> getErrors() {
        return (List<FPSError>) super.getErrors();
    }
}

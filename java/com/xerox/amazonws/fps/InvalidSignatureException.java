package com.xerox.amazonws.fps;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class InvalidSignatureException extends FPSException {
    public InvalidSignatureException(String invalidSignature, String requestURI) {
        super(invalidSignature + " is an invalid FPS signature (in " + requestURI + ')');
    }
}

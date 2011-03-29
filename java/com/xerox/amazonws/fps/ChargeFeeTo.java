package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public enum ChargeFeeTo implements Serializable {
    CALLER("Caller"), RECIPIENT("Recipient");

    private String value;

    ChargeFeeTo(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}

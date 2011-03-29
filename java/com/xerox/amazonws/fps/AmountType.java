package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public enum AmountType implements Serializable {
    MINIMUM("Minimum"),
    MAXIMUM("Maximum"),
    EXACT("Exact");

    private final String value;

    AmountType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AmountType fromValue(String v) {
        for (AmountType c : AmountType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
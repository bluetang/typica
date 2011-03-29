package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public enum TokenType implements Serializable {
    SINGLE_USE("SingleUse"),
    MULTI_USE("MultiUse"),
    RECURRING("Recurring"),
    UNRESTRICTED("Unrestricted");

    private final String value;

    TokenType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TokenType fromValue(String v) {
        for (TokenType c : TokenType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}

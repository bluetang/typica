package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public enum NotificationEventType implements Serializable {
    TRANSACTION_RESULTS("TransactionResults"),
    TOKEN_CANCELLATION("TokenCancellation");

    private final String value;

    NotificationEventType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NotificationEventType fromValue(String v) {
        for (NotificationEventType c : NotificationEventType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
package com.xerox.amazonws.fps;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public enum TransactionalRoleFilter {
    SENDER("Sender"),
    CALLER("Caller"),
    RECIPIENT("Recipient");

    private final String value;

    TransactionalRoleFilter(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransactionalRoleFilter fromValue(String v) {
        for (TransactionalRoleFilter c: TransactionalRoleFilter.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}

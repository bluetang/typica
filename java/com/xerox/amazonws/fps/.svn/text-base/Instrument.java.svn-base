package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public interface Instrument {
    Status getStatus();

    enum Status implements Serializable {
        ALL("All"),
        ACTIVE("Active"),
        CANCELLED("Cancelled");
        private final String value;

        Status(String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        public static Status fromValue(String v) {
            for (Status c : Status.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

    }

}

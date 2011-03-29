package com.xerox.amazonws.fps;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class OutstandingPrepaidLiability implements Serializable {
    private final Amount outstanding;
    private final Amount pending;

    public OutstandingPrepaidLiability(Amount outstanding, Amount pending) {
        this.outstanding = outstanding;
        this.pending = pending;
    }

    public Amount getOutstanding() {
        return outstanding;
    }

    public Amount getPending() {
        return pending;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OutstandingPrepaidLiability");
        sb.append("{outstanding=").append(outstanding);
        sb.append(", pending=").append(pending);
        sb.append('}');
        return sb.toString();
    }
}
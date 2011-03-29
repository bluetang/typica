package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class DebtBalance implements Serializable {
    private final Amount available;
    private final Amount pending;

    public DebtBalance(Amount available, Amount pending) {
        this.available = available;
        this.pending = pending;
    }

    public Amount getAvailable() {
        return available;
    }

    public Amount getPending() {
        return pending;
    }

    @Override
    public String toString() {
        return "DebtBalance{" +
                "available=" + available +
                ", pending=" + pending +
                '}';
    }
}
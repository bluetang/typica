package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class AccountBalance implements Serializable {
    private final Amount available;
    private final Amount pendingIn;
    private final Amount pendingOut;
    private final Amount disburse;
    private final Amount refund;

    public AccountBalance(Amount available, Amount pendingIn, Amount pendingOut, Amount disburse, Amount refund) {
        this.available = available;
        this.pendingIn = pendingIn;
        this.pendingOut = pendingOut;
        this.disburse = disburse;
        this.refund = refund;
    }

    public Amount getDisburse() {
        return disburse;
    }

    public Amount getRefund() {
        return refund;
    }

    public Amount getPendingIn() {
        return pendingIn;
    }

    public Amount getPendingOut() {
        return pendingOut;
    }

    public Amount getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "available=" + available +
                ", pendingIn=" + pendingIn +
                ", pendingOut=" + pendingOut +
                ", disburse=" + disburse +
                ", refund=" + refund +
                '}';
    }
}

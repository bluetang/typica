package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public enum FPSOperation implements Serializable {
    PAY("Pay"),
    REFUND("Refund"),
    SETTLE("Settle"),
    SETTLE_DEBT("SettleDebt"),
    WRITE_OFF_DEBT("WriteOffDebt"),
    FUND_PREPAID("FundPrepaid"),
    DEPOSIT_FUNDS("DepositFunds"),
    WITHDRAW_FUNDS("WithdrawFunds"),
    RESERVE("Reserve");

    private final String value;

    FPSOperation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FPSOperation fromValue(String v) {
        for (FPSOperation c: values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}

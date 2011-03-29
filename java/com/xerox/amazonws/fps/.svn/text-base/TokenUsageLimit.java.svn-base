package com.xerox.amazonws.fps;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class TokenUsageLimit implements Serializable {
    private final BigInteger count;
    private final Amount amount;
    private final BigInteger lastResetCount;
    private final Amount lastResetAmount;
    private final Date lastResetTimeStamp;

    public TokenUsageLimit(BigInteger count, Amount amount, BigInteger lastResetCount, Amount lastResetAmount, Date lastResetTimeStamp) {
        this.count = count;
        this.amount = amount;
        this.lastResetCount = lastResetCount;
        this.lastResetAmount = lastResetAmount;
        this.lastResetTimeStamp = lastResetTimeStamp;
    }

    public BigInteger getCount() {
        return count;
    }

    public Amount getAmount() {
        return amount;
    }

    public BigInteger getLastResetCount() {
        return lastResetCount;
    }

    public Amount getLastResetAmount() {
        return lastResetAmount;
    }

    public Date getLastResetTimeStamp() {
        return lastResetTimeStamp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TokenUsageLimit");
        sb.append("{count=").append(count);
        sb.append(", amount=").append(amount);
        sb.append(", lastResetCount=").append(lastResetCount);
        sb.append(", lastResetAmount=").append(lastResetAmount);
        sb.append(", lastResetTimeStamp=").append(lastResetTimeStamp);
        sb.append('}');
        return sb.toString();
    }
}
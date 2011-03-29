package com.xerox.amazonws.fps;

import java.util.Date;
import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class PostPaidInstrument implements Instrument {
    private final String creditInstrumentId;
    private final String creditSenderTokenId;
    private final String settlementTokenId;
    private final Date expiry;
    private final Address address;
    private final Status status;

    public PostPaidInstrument(String creditInstrumentId, String creditSenderTokenId, String settlementTokenId, Date expiry, Address address) {
        this.creditInstrumentId = creditInstrumentId;
        this.creditSenderTokenId = creditSenderTokenId;
        this.settlementTokenId = settlementTokenId;
        this.expiry = expiry;
        this.address = address;
        this.status = Status.ACTIVE;
    }

    public String getCreditInstrumentId() {
        return creditInstrumentId;
    }

    public String getCreditSenderTokenId() {
        return creditSenderTokenId;
    }

    public String getSettlementTokenId() {
        return settlementTokenId;
    }

    public Date getExpiry() {
        return expiry;
    }

    public Address getAddress() {
        return address;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PostPaidInstrument");
        sb.append("{creditInstrumentId='").append(creditInstrumentId).append('\'');
        sb.append(", creditSenderTokenId='").append(creditSenderTokenId).append('\'');
        sb.append(", settlementTokenId='").append(settlementTokenId).append('\'');
        sb.append(", expiry=").append(expiry);
        sb.append(", address=").append(address);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}

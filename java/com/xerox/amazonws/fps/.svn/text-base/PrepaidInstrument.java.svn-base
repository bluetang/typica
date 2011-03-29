package com.xerox.amazonws.fps;

import java.util.Date;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class PrepaidInstrument implements Instrument {
    private final String prepaidInstrumentId;
    private final String fundingInstrumentId;
    private final String prepaidSenderTokenId;
    private final Date expiry;
    private final Address address;
    private final Status status;

    public PrepaidInstrument(String prepaidInstrumentId, String fundingInstrumentId, String prepaidSenderTokenId, Date expiry, Address address) {
        this.prepaidInstrumentId = prepaidInstrumentId;
        this.fundingInstrumentId = fundingInstrumentId;
        this.prepaidSenderTokenId = prepaidSenderTokenId;
        this.expiry = expiry;
        this.address = address;
        this.status = Status.ACTIVE;
    }

    public String getPrepaidInstrumentId() {
        return prepaidInstrumentId;
    }

    public String getFundingInstrumentId() {
        return fundingInstrumentId;
    }

    public String getPrepaidSenderTokenId() {
        return prepaidSenderTokenId;
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
        sb.append("PrepaidInstrument");
        sb.append("{prepaidInstrumentId='").append(prepaidInstrumentId).append('\'');
        sb.append(", fundingInstrumentId='").append(fundingInstrumentId).append('\'');
        sb.append(", prepaidSenderTokenId='").append(prepaidSenderTokenId).append('\'');
        sb.append(", expiry='").append(expiry).append('\'');
        sb.append(", address=").append(address);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
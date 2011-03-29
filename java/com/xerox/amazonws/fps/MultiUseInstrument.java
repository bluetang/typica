package com.xerox.amazonws.fps;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.Date;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class MultiUseInstrument implements Instrument {
    private final String tokenId;
    private final Date expiry;
    private final Address address;
    private final Status status;

    public MultiUseInstrument(String tokenId, Date expiry, Address address) {
        this.tokenId = tokenId;
        this.expiry = expiry;
        this.address = address;
        this.status = Status.ACTIVE;
    }

    public String getTokenId() {
        return tokenId;
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
        sb.append("MultiUseInstrument");
        sb.append("{tokenId='").append(tokenId).append('\'');
        sb.append(", expiry='").append(expiry).append('\'');
        sb.append(", address=").append(address);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
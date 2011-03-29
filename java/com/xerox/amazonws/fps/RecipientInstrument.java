package com.xerox.amazonws.fps;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.Date;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class RecipientInstrument implements Instrument {
    private final String tokenId;
    private final String refundTokenId;
    private final Status status;

    public RecipientInstrument(String tokenId, String refundTokenId) {
        this.tokenId = tokenId;
        this.refundTokenId = refundTokenId;
        this.status = Status.ACTIVE;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getRefundTokenId() {
        return refundTokenId;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RecipientInstrument");
        sb.append("{tokenId='").append(tokenId).append('\'');
        sb.append(", refundTokenId='").append(refundTokenId).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
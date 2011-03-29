package com.xerox.amazonws.fps;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.Date;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class Token implements Serializable {
    private final String tokenId;
    private final String friendlyName;
    private final Status status;
    private final Date dateInstalled;
    private final String callerInstalled;
    private final TokenType type;
    private final String oldTokenId;
    private final String paymentReason;

    public Token(String tokenId, String friendlyName, Status status, Date dateInstalled, String callerInstalled,
                 TokenType tokenType, String oldTokenId, String paymentReason) {
        this.tokenId = tokenId;
        this.friendlyName = friendlyName;
        this.status = status;
        this.dateInstalled = dateInstalled;
        this.callerInstalled = callerInstalled;
        this.type = tokenType;
        this.oldTokenId = oldTokenId;
        this.paymentReason = paymentReason;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public Status getStatus() {
        return status;
    }

    public Date getDateInstalled() {
        return dateInstalled;
    }

    public String getCallerInstalled() {
        return callerInstalled;
    }

    public TokenType getType() {
        return type;
    }

    public String getOldTokenId() {
        return oldTokenId;
    }

    public String getPaymentReason() {
        return paymentReason;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenId='" + tokenId + '\'' +
                ", friendlyName='" + friendlyName + '\'' +
                ", status=" + status +
                ", dateInstalled=" + dateInstalled +
                ", callerInstalled='" + callerInstalled + '\'' +
                ", type=" + type +
                ", oldTokenId='" + oldTokenId + '\'' +
                ", paymentReason='" + paymentReason + '\'' +
                '}';
    }

    public enum Status implements Serializable {
        ACTIVE("Active"),
        INACTIVE("Inactive");

        private final String value;

        Status(String value) {
            this.value = value;
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

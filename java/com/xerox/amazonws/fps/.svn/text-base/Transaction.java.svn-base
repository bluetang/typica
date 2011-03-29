package com.xerox.amazonws.fps;

import java.io.Serializable;
import java.util.Date;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class Transaction implements Serializable {
    private final String transactionID;
    private final Status status;
    private final String detail;
    private final Date dateReceived, dateCompleted;
    private final Amount transactionAmount;
    private final FPSOperation operation;
    private final PaymentMethod paymentMethod;
    private final String senderName;
    private final String callerName;
    private final String recipientName;
    private final Amount fees;
    private final Amount balance;
    private final String callerTokenId;
    private final String senderTokenId;
    private final String recipientTokenId;


    public Transaction(String transactionID, Status status, String detail) {
        this.transactionID = transactionID;
        this.status = status;
        this.detail = detail;
        this.dateReceived = null;
        this.dateCompleted = null;
        this.transactionAmount = null;
        this.operation = null;
        this.paymentMethod = null;
        this.senderName = null;
        this.callerName = null;
        this.recipientName = null;
        this.fees = null;
        this.balance = null;
        this.callerTokenId = null;
        this.senderTokenId = null;
        this.recipientTokenId = null;
    }

    public Transaction(String transactionID, Status status, Date dateReceived, Date dateCompleted,
                       Amount transactionAmount, FPSOperation operation, PaymentMethod paymentMethod,
                       String senderName, String callerName, String recipientName, Amount fees, Amount balance,
                       String callerTokenId, String senderTokenId, String recipientTokenId) {
        this.transactionID = transactionID;
        this.status = status;
        this.detail = null;
        this.dateReceived = dateReceived;
        this.dateCompleted = dateCompleted;
        this.transactionAmount = transactionAmount;
        this.operation = operation;
        this.paymentMethod = paymentMethod;
        this.senderName = senderName;
        this.callerName = callerName;
        this.recipientName = recipientName;
        this.fees = fees;
        this.balance = balance;
        this.callerTokenId = callerTokenId;
        this.senderTokenId = senderTokenId;
        this.recipientTokenId = recipientTokenId;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public Status getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    public FPSOperation getOperation() {
        return operation;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getCallerName() {
        return callerName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public Amount getFees() {
        return fees;
    }

    public Amount getBalance() {
        return balance;
    }

    public String getCallerTokenId() {
        return callerTokenId;
    }

    public String getSenderTokenId() {
        return senderTokenId;
    }

    public String getRecipientTokenId() {
        return recipientTokenId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", status=" + status +
                ", detail='" + detail + '\'' +
                ", dateReceived=" + dateReceived +
                ", dateCompleted=" + dateCompleted +
                ", transactionAmount=" + transactionAmount +
                ", operation=" + operation +
                ", paymentMethod=" + paymentMethod +
                ", senderName='" + senderName + '\'' +
                ", callerName='" + callerName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", fees=" + fees +
                ", balance=" + balance +
                ", callerTokenId='" + callerTokenId + '\'' +
                ", senderTokenId='" + senderTokenId + '\'' +
                ", recipientTokenId='" + recipientTokenId + '\'' +
                '}';
    }

    public enum Status implements Serializable {
        RESERVED("Reserved"),
        SUCCESS("Success"),
        FAILURE("Failure"),
        INITIATED("Initiated"),
        REINITIATED("Reinitiated"),
        TEMPORARY_DECLINE("TemporaryDecline");

        private final String value;

        Status(String v) {
            value = v;
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

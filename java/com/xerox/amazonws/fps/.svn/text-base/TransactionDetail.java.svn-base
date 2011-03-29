package com.xerox.amazonws.fps;

import java.io.Serializable;
import java.util.Date;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class TransactionDetail implements Serializable {
    private final String transactionId;
    private final Date callerTransactionDate;
    private final Date dateReceived;
    private final Date dateCompleted;
    private final Amount transactionAmount;
    private final Amount fees;
    private final String callerTokenId;
    private final String senderTokenId;
    private final String recipientTokenId;
    private final String prepaidInstrumentId;
    private final String creditInstrumentId;
    private final FPSOperation operation;
    private final PaymentMethod paymentMethod;
    private final Transaction.Status status;
    private final String errorCode;
    private final String errorMessage;
    private final String metaData;
    private final String senderName;
    private final String callerName;
    private final String recipientName;
//        private final List<TransactionPart> transactionParts;
//        private final List<RelatedTransaction> relatedTransactions;
//        private final List<StatusChange> statusHistory;

    public TransactionDetail(String transactionId, Date callerTransactionDate, Date dateReceived, Date dateCompleted,
                  Amount transactionAmount, Amount fees,
                  String callerTokenId, String senderTokenId, String recipientTokenId,
                  String prepaidInstrumentId, String creditInstrumentId, FPSOperation operation,
                  PaymentMethod paymentMethod, Transaction.Status status, String errorCode, String errorMessage,
                  String metaData, String senderName, String callerName, String recipientName) {
        this.transactionId = transactionId;
        this.callerTransactionDate = callerTransactionDate;
        this.dateReceived = dateReceived;
        this.dateCompleted = dateCompleted;
        this.transactionAmount = transactionAmount;
        this.fees = fees;
        this.callerTokenId = callerTokenId;
        this.senderTokenId = senderTokenId;
        this.recipientTokenId = recipientTokenId;
        this.prepaidInstrumentId = prepaidInstrumentId;
        this.creditInstrumentId = creditInstrumentId;
        this.operation = operation;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.metaData = metaData;
        this.senderName = senderName;
        this.callerName = callerName;
        this.recipientName = recipientName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Date getCallerTransactionDate() {
        return callerTransactionDate;
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

    public Amount getFees() {
        return fees;
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

    public String getPrepaidInstrumentId() {
        return prepaidInstrumentId;
    }

    public String getCreditInstrumentId() {
        return creditInstrumentId;
    }

    public FPSOperation getOperation() {
        return operation;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Transaction.Status getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getMetaData() {
        return metaData;
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

    @Override
    public String toString() {
        return "TransactionDetail{" +
                "transactionId='" + transactionId + '\'' +
                ", callerTransactionDate=" + callerTransactionDate +
                ", dateReceived=" + dateReceived +
                ", dateCompleted=" + dateCompleted +
                ", transactionAmount=" + transactionAmount +
                ", fees=" + fees +
                ", callerTokenId='" + callerTokenId + '\'' +
                ", senderTokenId='" + senderTokenId + '\'' +
                ", recipientTokenId='" + recipientTokenId + '\'' +
                ", prepaidInstrumentId='" + prepaidInstrumentId + '\'' +
                ", creditInstrumentId='" + creditInstrumentId + '\'' +
                ", operation=" + operation +
                ", paymentMethod=" + paymentMethod +
                ", status=" + status +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", metaData='" + metaData + '\'' +
                ", senderName='" + senderName + '\'' +
                ", callerName='" + callerName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                '}';
    }
}

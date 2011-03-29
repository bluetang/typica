package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class TransactionResult implements Serializable {
    private final String transactionId;
    private final FPSOperation operation;
    private final String callerReference;
    private final Transaction.Status status;

    public TransactionResult(String transactionId, FPSOperation operation, String callerReference, Transaction.Status status) {
        this.transactionId = transactionId;
        this.operation = operation;
        this.callerReference = callerReference;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public FPSOperation getOperation() {
        return operation;
    }

    public String getCallerReference() {
        return callerReference;
    }

    public Transaction.Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TransactionResult");
        sb.append("{transactionId='").append(transactionId).append('\'');
        sb.append(", operation=").append(operation);
        sb.append(", callerReference='").append(callerReference).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
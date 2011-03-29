package com.xerox.amazonws.fps;

import java.io.Serializable;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class PaymentInstructionDetail implements Serializable {
    private final Token token;
    private final String paymentInstruction;
    private final String accountId;
    private final String tokenFriendlyName;

    public PaymentInstructionDetail(Token token, String paymentInstruction, String accountId, String tokenFriendlyName) {
        this.token = token;
        this.paymentInstruction = paymentInstruction;
        this.accountId = accountId;
        this.tokenFriendlyName = tokenFriendlyName;
    }

    public Token getToken() {
        return token;
    }

    public String getPaymentInstruction() {
        return paymentInstruction;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getTokenFriendlyName() {
        return tokenFriendlyName;
    }

    @Override
    public String toString() {
        return "PaymentInstructionDetail{" +
                "token=" + token +
                ", paymentInstruction='" + paymentInstruction + '\'' +
                ", accountId='" + accountId + '\'' +
                ", tokenFriendlyName='" + tokenFriendlyName + '\'' +
                '}';
    }
}

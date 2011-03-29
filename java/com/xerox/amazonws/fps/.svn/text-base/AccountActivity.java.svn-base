package com.xerox.amazonws.fps;

import com.xerox.amazonws.sdb.DataUtils;
import com.xerox.amazonws.typica.fps.jaxb.GetAccountActivityResponse;
import org.apache.http.client.methods.HttpGet;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public class AccountActivity implements Iterable<Transaction>, Serializable {
    private BigInteger totalTransactions;
    private List<Transaction> transactions;
    private Date nextStartDate;
    private final FPSOperation filter;
    private final PaymentMethod paymentMethod;
    private final int maxBatchSize;
    private final Date endDate;
    private final Transaction.Status transactionStatus;
    private final FlexiblePaymentsService fps;

    public AccountActivity(Date nextStartDate, BigInteger totalTransactions, List<Transaction> transactions,
                           FPSOperation filter, PaymentMethod paymentMethod, int maxBatchSize,
                           Date endDate, Transaction.Status transactionStatus,
                           FlexiblePaymentsService fps) {
        this.nextStartDate = nextStartDate;
        this.totalTransactions = totalTransactions;
        this.transactions = transactions;
        this.filter = filter;
        this.paymentMethod = paymentMethod;
        this.maxBatchSize = maxBatchSize;
        this.endDate = endDate;
        this.transactionStatus = transactionStatus;
        this.fps = fps;
    }

    public BigInteger getTotalTransactions() {
        return totalTransactions;
    }

    public Iterator<Transaction> iterator() {
        return new AccountActivityIterator(nextStartDate, transactions, filter, paymentMethod,
                maxBatchSize, endDate, transactionStatus, fps);
    }

    class AccountActivityIterator implements Iterator<Transaction> {
        private Iterator<Transaction> transactionsIterator;
        private Date nextStartDate;
        private final FPSOperation filter;
        private final PaymentMethod paymentMethod;
        private final int maxBatchSize;
        private final Date endDate;
        private final Transaction.Status transactionStatus;
        private final FlexiblePaymentsService fps;

        public AccountActivityIterator(Date nextStartDate, List<Transaction> transactions, FPSOperation filter,
                                       PaymentMethod paymentMethod, int maxBatchSize, Date endDate,
                                       Transaction.Status transactionStatus, FlexiblePaymentsService fps) {
            this.nextStartDate = nextStartDate;
            this.transactionsIterator = transactions.iterator();
            this.filter = filter;
            this.paymentMethod = paymentMethod;
            this.maxBatchSize = maxBatchSize;
            this.endDate = endDate;
            this.transactionStatus = transactionStatus;
            this.fps = fps;
        }

        public boolean hasNext() {
            return transactionsIterator.hasNext() || nextStartDate != null;
        }

        public Transaction next() {
            if (transactionsIterator.hasNext())
                return transactionsIterator.next();
            if (nextStartDate == null)
                throw new NoSuchElementException();

            HttpGet method = new HttpGet();
            Map<String, String> params = new HashMap<String, String>();
            if (filter != null)
                params.put("Operation", filter.value());
            if (paymentMethod != null)
                params.put("PaymentMethod", paymentMethod.value());
            if (maxBatchSize != 0)
                params.put("MaxBatchSize", Integer.toString(maxBatchSize));
            params.put("StartDate", DataUtils.encodeDate(nextStartDate));
            if (endDate != null)
                params.put("EndDate", DataUtils.encodeDate(endDate));
            if (transactionStatus != null)
                params.put("Status", transactionStatus.value());
            try {
                GetAccountActivityResponse response =
                        fps.makeRequestInt(method, "GetAccountActivity", params, GetAccountActivityResponse.class);
                nextStartDate = response.getStartTimeForNextTransaction().toGregorianCalendar().getTime();
                totalTransactions = response.getResponseBatchSize();
                List<com.xerox.amazonws.typica.fps.jaxb.Transaction> rawTransactions = response.getTransactions();
                List<Transaction> transactions = new ArrayList<Transaction>(rawTransactions.size());
                for (com.xerox.amazonws.typica.fps.jaxb.Transaction txn : rawTransactions) {
                    com.xerox.amazonws.typica.fps.jaxb.Amount txnAmount = txn.getTransactionAmount();
                    com.xerox.amazonws.typica.fps.jaxb.Amount fees = txn.getFees();
                    com.xerox.amazonws.typica.fps.jaxb.Amount balance = txn.getBalance();
                    transactions.add(new Transaction(
                            txn.getTransactionId(), Transaction.Status.fromValue(txn.getStatus().value()),
                            txn.getDateReceived().toGregorianCalendar().getTime(),
                            txn.getDateCompleted().toGregorianCalendar().getTime(),
                            new Amount(new BigDecimal(txnAmount.getAmount()), txnAmount.getCurrencyCode().toString()),
                            FPSOperation.fromValue(txn.getOperation().value()),
                            PaymentMethod.fromValue(txn.getPaymentMethod().value()),
                            txn.getSenderName(), txn.getCallerName(), txn.getRecipientName(),
                            new Amount(new BigDecimal(fees.getAmount()), fees.getCurrencyCode().toString()),
                            new Amount(new BigDecimal(balance.getAmount()), balance.getCurrencyCode().toString()),
                            txn.getCallerTokenId(), txn.getSenderTokenId(), txn.getRecipientTokenId()
                    ));
                }
                transactionsIterator = transactions.iterator();
            } catch (Exception e) {
                throw new NoSuchElementException(e.getMessage());
			}
            return transactionsIterator.next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

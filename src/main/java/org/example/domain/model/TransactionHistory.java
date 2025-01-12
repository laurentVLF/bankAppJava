package org.example.domain.model;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public final class TransactionHistory {
    private final String transactionId;
    private final TransactionType transactionType;
    private final BigDecimal amount;
    private final LocalDateTime creationDate = LocalDateTime.now();

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionHistory(TransactionType transactionType, BigDecimal amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.transactionType = transactionType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("TransactionHistoric'{'transactionId=''{0}'', transactionType={1}, amount={2}, creationDate={3}'}'", transactionId, transactionType, amount, creationDate);
    }
}

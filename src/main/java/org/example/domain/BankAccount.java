package org.example.domain;

import org.example.domain.model.TransactionHistory;
import org.example.domain.model.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class BankAccount {

    private final String id = UUID.randomUUID().toString();
    private final List<TransactionHistory> transactionsHistory = new ArrayList<>();

    List<TransactionHistory> getTransactionsHistory() {
        for (TransactionHistory transactionHistory : transactionsHistory) {
            System.out.println(transactionHistory.toString());
        }

        return transactionsHistory;
    }

    public BankAccount() {}

    void deposit(BigDecimal amount) {
        System.out.println("deposit: " + amount.toString());
        this.transactionsHistory
                .add(new TransactionHistory(TransactionType.DEPOSIT, amount));
    }

    void withdraw(BigDecimal amount) {
        System.out.println("withdraw: " + amount.toString());
        this.transactionsHistory
                .add(new TransactionHistory(TransactionType.WITHDRAW, amount));
    }
}

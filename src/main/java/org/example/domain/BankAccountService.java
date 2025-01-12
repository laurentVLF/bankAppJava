package org.example.domain;

import org.example.domain.model.TransactionAmount;
import org.example.domain.model.TransactionHistory;
import org.example.domain.model.TransactionType;
import org.example.domain.exception.InsufficientBalanceException;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankAccountService {

    private final BankAccount bankAccount;

    public BankAccountService(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BigDecimal getBalance() {
        Map<TransactionType, BigDecimal> totals = bankAccount.getTransactionsHistory()
                .stream()
                .collect(Collectors.groupingBy(
                        TransactionHistory::getTransactionType,
                        Collectors.reducing(BigDecimal.ZERO, TransactionHistory::getAmount, BigDecimal::add)
                ));

        BigDecimal totalDeposit = totals.getOrDefault(TransactionType.DEPOSIT, BigDecimal.ZERO);
        BigDecimal totalWithdraw = totals.getOrDefault(TransactionType.WITHDRAW, BigDecimal.ZERO);
        return totalDeposit.subtract(totalWithdraw);
    }

    public List<TransactionHistory> getTransactionsHistory() {
        return bankAccount.getTransactionsHistory();
    }

    public void makeDeposit(TransactionAmount amount) {
        bankAccount.deposit(amount.value());
    }

    public void makeWithdrawal(TransactionAmount amount) throws InsufficientBalanceException {
        BigDecimal balance = getBalance();
        if (balance.subtract(amount.value()).compareTo(BigDecimal.ZERO) >= 0) {
            bankAccount.withdraw(amount.value());
        } else throw new InsufficientBalanceException(MessageFormat.format("Insufficient balance -> {0}", balance.subtract(amount.value())));
    }
}

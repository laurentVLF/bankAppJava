package org.example.domain;

import org.example.domain.exception.InsufficientBalanceException;
import org.example.domain.model.TransactionAmount;
import org.example.domain.model.TransactionHistory;
import org.example.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountServiceTest {

    private BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        BankAccount bankAccount = new BankAccount();
        bankAccountService = new BankAccountService(bankAccount);
    }

    @Nested
    class DepositTest {

        @Test
        void givenPositiveAmountToDepositShouldIncreaseTheBalance() {
            bankAccountService.makeDeposit(new TransactionAmount(BigDecimal.valueOf(100.0)));
            assertEquals(BigDecimal.valueOf(100.0), bankAccountService.getBalance());
        }

        @Test
        void givenNegativeAmountShouldThrowAException() {
            Throwable throwable =  assertThrows(Throwable.class, () -> {
                bankAccountService.makeDeposit(new TransactionAmount(BigDecimal.valueOf(-100.0)));
            });
            assertEquals(IllegalArgumentException.class, throwable.getClass());
            assertEquals("Amount must be greater than zero", throwable.getMessage());
            assertEquals(BigDecimal.valueOf(0), bankAccountService.getBalance());
        }
    }

    @Nested
    class WithdrawalTest {

        @BeforeEach
        void setUp() {
            bankAccountService.makeDeposit(new TransactionAmount(BigDecimal.valueOf(100.0)));
        }

        @Test
        void givenPositiveAmountToWithdrawShouldDecreaseTheBalance() throws InsufficientBalanceException {
            bankAccountService.makeWithdrawal(new TransactionAmount(BigDecimal.valueOf(100.0)));
            assertEquals(BigDecimal.valueOf(0.0), bankAccountService.getBalance());
        }

        @Test
        void givenNegativeAmountToWithdrawShouldThrowException() {
            Throwable throwable =  assertThrows(Throwable.class, () -> {
                bankAccountService.makeWithdrawal(new TransactionAmount(BigDecimal.valueOf(-100.0)));
            });
            assertEquals(IllegalArgumentException.class, throwable.getClass());
            assertEquals("Amount must be greater than zero", throwable.getMessage());
            assertEquals(BigDecimal.valueOf(100.0), bankAccountService.getBalance());
        }

        @Test
        void givenPositiveAmountToWithdrawWhenInsufficientBalanceShouldThrowException() throws InsufficientBalanceException {
            Throwable throwable =  assertThrows(Throwable.class, () -> {
                bankAccountService.makeWithdrawal(new TransactionAmount(BigDecimal.valueOf(120.0)));
            });
            assertEquals(InsufficientBalanceException.class, throwable.getClass());
            assertEquals("Insufficient balance -> -20", throwable.getMessage());
            assertEquals(BigDecimal.valueOf(100.0), bankAccountService.getBalance());
        }

    }

    @Nested
    class TransactionHistoryTest {

        private final List<TransactionHistory> transactionHistoryList = new ArrayList<>();

        @BeforeEach
        void setUp() {
            populateTransactionsData();
            initListTransactions();
        }

        @Test
        void givenAListOfActionShouldReturnTransactionHistory() {
            assertFalse(bankAccountService.getTransactionsHistory().isEmpty());
            assertEquals(transactionHistoryList.size(), bankAccountService.getTransactionsHistory().size());
            assertEquals(BigDecimal.valueOf(200.0), bankAccountService.getBalance());
        }

        private void populateTransactionsData() {
            transactionHistoryList.add(new TransactionHistory(TransactionType.DEPOSIT, BigDecimal.valueOf(100.0)));
            transactionHistoryList.add(new TransactionHistory(TransactionType.DEPOSIT, BigDecimal.valueOf(100.0)));
            transactionHistoryList.add(new TransactionHistory(TransactionType.WITHDRAW, BigDecimal.valueOf(100.0)));
            transactionHistoryList.add(new TransactionHistory(TransactionType.DEPOSIT, BigDecimal.valueOf(100.0)));
            transactionHistoryList.add(new TransactionHistory(TransactionType.WITHDRAW, BigDecimal.valueOf(100.0)));
            transactionHistoryList.add(new TransactionHistory(TransactionType.DEPOSIT, BigDecimal.valueOf(100.0)));
        }

        private void initListTransactions() {
            transactionHistoryList.forEach(transactionHistory -> {
                if (transactionHistory.getTransactionType().equals(TransactionType.DEPOSIT)) {
                    bankAccountService.makeDeposit(new TransactionAmount(transactionHistory.getAmount()));
                } else if (transactionHistory.getTransactionType().equals(TransactionType.WITHDRAW)) {
                    try {
                        bankAccountService.makeWithdrawal(new TransactionAmount(transactionHistory.getAmount()));
                    } catch (InsufficientBalanceException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        }
    }
}
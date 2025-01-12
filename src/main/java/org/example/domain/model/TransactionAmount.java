package org.example.domain.model;

import java.math.BigDecimal;

public record TransactionAmount(BigDecimal value) {

    public TransactionAmount {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}

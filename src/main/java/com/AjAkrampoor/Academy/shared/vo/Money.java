package com.AjAkrampoor.Academy.shared.vo;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class Money {
    private BigDecimal amount;

    protected Money() {
    }

    public Money(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Money amount cannot be null");
        }
        validateAmount(amount);
        this.amount = amount;
    }

    public Money add(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot add a null Money object");
        }
        BigDecimal newAmount = this.amount.add(other.amount);
        validateAmount(newAmount);
        return new Money(newAmount);
    }

    public Money subtract(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot subtract a null Money object");
        }
        BigDecimal newAmount = this.amount.subtract(other.amount);
        validateAmount(newAmount);
        return new Money(newAmount);
    }

    public Money multiply(long multiplier) {
        BigDecimal newAmount = this.amount.multiply(BigDecimal.valueOf(multiplier));
        validateAmount(newAmount);
        return new Money(newAmount);
    }

    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount must be non-negative");
        }

        if (amount.scale() > 2) {
            throw new IllegalArgumentException(
                    "Money amount cannot have more than 2 digits after decimal: " + amount
            );
        }

        int integerDigits = amount.abs().toBigInteger().toString().length();
        if (integerDigits > 9) {
            throw new IllegalArgumentException(
                    "Money amount cannot have more than 9 digits before decimal: " + amount
            );
        }
    }
}


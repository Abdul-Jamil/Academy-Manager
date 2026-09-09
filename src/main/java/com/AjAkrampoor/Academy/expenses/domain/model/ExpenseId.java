package com.AjAkrampoor.Academy.expenses.domain.model;

import java.util.Objects;
import java.util.UUID;

public class ExpenseId {
    private String id;

    private ExpenseId() {
    }

    public ExpenseId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static ExpenseId newId() {
        return new ExpenseId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseId expenseId = (ExpenseId) o;
        return Objects.equals(id, expenseId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

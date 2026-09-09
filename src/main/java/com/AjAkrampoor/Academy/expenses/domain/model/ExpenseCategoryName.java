package com.AjAkrampoor.Academy.expenses.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ExpenseCategoryName {
    private String value;

    protected ExpenseCategoryName() {
        // For JPA
    }

    public ExpenseCategoryName(String value) {
        if (value == null || value.trim().length() < 3) {
            throw new IllegalArgumentException("Branch name cannot be less than 3 letters");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("Branch name cannot be more than 100 letters");
        }

        this.value = value.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseCategoryName that = (ExpenseCategoryName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public String getValue() {
        return value;
    }

}

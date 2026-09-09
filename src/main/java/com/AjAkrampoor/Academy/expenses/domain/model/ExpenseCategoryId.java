package com.AjAkrampoor.Academy.expenses.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ExpenseCategoryId implements Serializable {
    private String id;

    private ExpenseCategoryId() {
    }

    public ExpenseCategoryId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static ExpenseCategoryId newId() {
        return new ExpenseCategoryId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseCategoryId that = (ExpenseCategoryId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id;
    }
}

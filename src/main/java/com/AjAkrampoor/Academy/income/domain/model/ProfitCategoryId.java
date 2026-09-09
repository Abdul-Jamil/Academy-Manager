package com.AjAkrampoor.Academy.income.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProfitCategoryId implements Serializable {

    private String id;

    protected ProfitCategoryId() {
        // For JPA
    }

    public ProfitCategoryId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static ProfitCategoryId newId() {
        return new ProfitCategoryId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfitCategoryId that = (ProfitCategoryId) o;

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

package com.AjAkrampoor.Academy.income.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProfitId implements Serializable {

    private String id;

    protected ProfitId() {
        // For JPA
    }

    public ProfitId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static ProfitId newId() {
        return new ProfitId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfitId profitId = (ProfitId) o;

        return Objects.equals(id, profitId.id);
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

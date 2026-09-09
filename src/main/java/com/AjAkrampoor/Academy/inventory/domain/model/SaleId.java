package com.AjAkrampoor.Academy.inventory.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SaleId implements Serializable {

    private String id;

    protected SaleId() {}

    public SaleId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static SaleId newId() {
        return new SaleId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleId saleId = (SaleId) o;
        return Objects.equals(id, saleId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.AjAkrampoor.Academy.inventory.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SaleItemId implements Serializable {

    private String id;

    protected SaleItemId() {}

    public SaleItemId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static SaleItemId newId() {
        return new SaleItemId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleItemId that = (SaleItemId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

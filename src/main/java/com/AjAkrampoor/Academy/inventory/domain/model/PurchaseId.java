package com.AjAkrampoor.Academy.inventory.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PurchaseId implements Serializable {

    private String id;

    protected PurchaseId() {}

    public PurchaseId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static PurchaseId newId() {
        return new PurchaseId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseId that = (PurchaseId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}

package com.AjAkrampoor.Academy.inventory.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PurchaseItemId implements Serializable {

    private String id;

    protected PurchaseItemId() {}

    public PurchaseItemId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static PurchaseItemId newId() {
        return new PurchaseItemId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseItemId that = (PurchaseItemId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

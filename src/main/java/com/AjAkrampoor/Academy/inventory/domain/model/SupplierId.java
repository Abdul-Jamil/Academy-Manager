package com.AjAkrampoor.Academy.inventory.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SupplierId implements Serializable {

    private String id;

    protected SupplierId() {}

    public SupplierId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static SupplierId newId() {
        return new SupplierId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierId that = (SupplierId) o;
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

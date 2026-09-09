package com.AjAkrampoor.Academy.bills.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class BillId implements Serializable {
    private String value;

    private BillId() {
    }

    public BillId(UUID value) {
        this.value = String.valueOf(value);
    }

    public static BillId newId() {
        return new BillId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BillId billId = (BillId) o;
        return Objects.equals(value, billId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

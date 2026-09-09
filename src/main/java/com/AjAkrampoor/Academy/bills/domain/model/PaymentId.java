package com.AjAkrampoor.Academy.bills.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PaymentId implements Serializable {
    private String value;

    private PaymentId() {
    }

    public PaymentId(UUID value) {
        this.value = String.valueOf(value);
    }

    public static PaymentId newId() {
        return new PaymentId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PaymentId paymentId = (PaymentId) o;
        return Objects.equals(value, paymentId.value);
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

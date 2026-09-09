package com.AjAkrampoor.Academy.salaries.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SalaryPaymentId implements Serializable {

    private String id;

    protected SalaryPaymentId() {
    }

    public SalaryPaymentId(UUID value) {
        this.id = String.valueOf(
                Objects.requireNonNull(value, "Salary payment ID cannot be null")
        );
    }

    public static SalaryPaymentId newId() {
        return new SalaryPaymentId(UUID.randomUUID());
    }

    public static SalaryPaymentId fromString(String id) {
        try {
            return new SalaryPaymentId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid SalaryPaymentId format: " + id,
                    e
            );
        }
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalaryPaymentId that = (SalaryPaymentId) o;

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
package com.AjAkrampoor.Academy.salaries.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SalaryContractId implements Serializable {

    private String id;

    protected SalaryContractId() {
    }

    public SalaryContractId(UUID value) {
        this.id = String.valueOf(
                Objects.requireNonNull(value, "Salary contract ID cannot be null")
        );
    }

    public static SalaryContractId newId() {
        return new SalaryContractId(UUID.randomUUID());
    }

    public static SalaryContractId fromString(String id) {
        try {
            return new SalaryContractId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid SalaryContractId format: " + id,
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

        SalaryContractId that = (SalaryContractId) o;

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
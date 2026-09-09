package com.AjAkrampoor.Academy.salaries.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MonthlySalaryId implements Serializable {

    private String id;

    protected MonthlySalaryId() {
    }

    public MonthlySalaryId(UUID value) {
        this.id = String.valueOf(
                Objects.requireNonNull(value, "Monthly salary ID cannot be null")
        );
    }

    public static MonthlySalaryId newId() {
        return new MonthlySalaryId(UUID.randomUUID());
    }

    public static MonthlySalaryId fromString(String id) {
        try {
            return new MonthlySalaryId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid MonthlySalaryId format: " + id,
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

        MonthlySalaryId that = (MonthlySalaryId) o;

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
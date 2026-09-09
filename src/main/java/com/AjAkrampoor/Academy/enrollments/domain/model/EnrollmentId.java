package com.AjAkrampoor.Academy.enrollments.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class EnrollmentId implements Serializable {
    private String value;

    private EnrollmentId() {
    }

    public EnrollmentId(UUID value) {
        this.value = String.valueOf(value);
    }

    public static EnrollmentId newId() {
        return new EnrollmentId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(value, that.value);
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

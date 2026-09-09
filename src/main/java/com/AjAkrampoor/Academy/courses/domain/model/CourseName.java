package com.AjAkrampoor.Academy.courses.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class CourseName {

    private String value;

    protected CourseName() {
    }

    public CourseName(String value) {

        if (value == null || value.trim().length() < 3) {
            throw new IllegalArgumentException("Course name cannot be less than 3 letters");
        }

        if (value.trim().length() > 80) {
            throw new IllegalArgumentException("Course name cannot be more than 80 letters");
        }

        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseName that = (CourseName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

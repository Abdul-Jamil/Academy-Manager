package com.AjAkrampoor.Academy.courses.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CourseId implements Serializable {

    private String value;

    protected CourseId() {
    }

    public CourseId(UUID value) {
        this.value = String.valueOf(Objects.requireNonNull(value, "Course ID cannot be null"));
    }

    public static CourseId newId() {
        return new CourseId(UUID.randomUUID());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseId courseId = (CourseId) o;
        return Objects.equals(value, courseId.value);
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

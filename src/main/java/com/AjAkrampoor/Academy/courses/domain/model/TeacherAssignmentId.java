package com.AjAkrampoor.Academy.courses.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class TeacherAssignmentId implements Serializable {

    private String id;

    protected TeacherAssignmentId() {
    }

    public TeacherAssignmentId(UUID value) {
        this.id = String.valueOf(
                Objects.requireNonNull(
                        value,
                        "Teacher assignment ID cannot be null"
                )
        );
    }

    public static TeacherAssignmentId newId() {
        return new TeacherAssignmentId(UUID.randomUUID());
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeacherAssignmentId that = (TeacherAssignmentId) o;
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
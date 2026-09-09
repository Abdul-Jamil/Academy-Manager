package com.AjAkrampoor.Academy.courses.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ClassId implements Serializable {
    private String id;

    private ClassId() {
    }

    public ClassId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static ClassId newId() {
        return new ClassId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassId classId = (ClassId) o;
        return Objects.equals(id, classId.id);
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

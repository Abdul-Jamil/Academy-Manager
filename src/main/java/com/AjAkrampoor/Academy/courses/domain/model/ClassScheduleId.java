package com.AjAkrampoor.Academy.courses.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ClassScheduleId implements Serializable {

    private String id;

    protected ClassScheduleId() {
    }

    public ClassScheduleId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static ClassScheduleId newId() {
        return new ClassScheduleId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassScheduleId that = (ClassScheduleId) o;
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

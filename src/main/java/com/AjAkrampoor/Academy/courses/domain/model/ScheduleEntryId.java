package com.AjAkrampoor.Academy.courses.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ScheduleEntryId implements Serializable {

    private String id;

    protected ScheduleEntryId() {
    }

    public ScheduleEntryId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static ScheduleEntryId newId() {
        return new ScheduleEntryId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleEntryId that = (ScheduleEntryId) o;
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

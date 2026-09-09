package com.AjAkrampoor.Academy.sessions.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class DayOffId implements Serializable {
    private String id;

    private DayOffId() {
    }

    public DayOffId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static DayOffId newId() {
        return new DayOffId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DayOffId dayOffId = (DayOffId) o;
        return Objects.equals(id, dayOffId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id;
    }
}

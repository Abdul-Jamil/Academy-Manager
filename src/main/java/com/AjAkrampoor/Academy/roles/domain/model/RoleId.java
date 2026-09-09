package com.AjAkrampoor.Academy.roles.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class RoleId implements Serializable {
    private String value;

    private RoleId() {
    }

    public RoleId(UUID value) {
        this.value = String.valueOf(value);
    }

    public static RoleId newId() {
        return new RoleId(UUID.randomUUID());
    }

    public static RoleId fromString(String id) {
        try {
            return new RoleId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid RoleId format: " + id + ". Must be a valid UUID.", e);
        }
    }

    public String asString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleId roleId = (RoleId) o;
        return Objects.equals(value, roleId.value);
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

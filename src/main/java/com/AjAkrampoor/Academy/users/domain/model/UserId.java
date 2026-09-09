package com.AjAkrampoor.Academy.users.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserId implements Serializable {
    private String id;

    private UserId() {
    }

    public UserId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId fromString(String id) {
        try {
            return new UserId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UserId format: " + id + ". Must be a valid UUID.", e);
        }
    }

    public String asString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(id, userId.id);
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

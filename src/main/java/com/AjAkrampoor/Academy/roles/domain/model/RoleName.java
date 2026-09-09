package com.AjAkrampoor.Academy.roles.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class RoleName {
    private String value;

    private RoleName() {
        // For JPA
    }

    public RoleName(String value) {
        if (value == null || value.trim().length() < 3) {
            throw new IllegalArgumentException("Role name cannot be less than 3 letters");
        }
        if (value.trim().length() > 30) {
            throw new IllegalArgumentException("Role name cannot be more than 30 letters");
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
        RoleName roleName = (RoleName) o;
        return Objects.equals(value, roleName.value);
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

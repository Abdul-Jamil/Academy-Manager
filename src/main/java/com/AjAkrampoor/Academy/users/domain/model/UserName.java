package com.AjAkrampoor.Academy.users.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class UserName {
    private String username;

    private UserName() {
    }

    public UserName(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        String normalized = username.trim().toLowerCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (normalized.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long");
        }
        if (normalized.length() > 50) {
            throw new IllegalArgumentException("Username cannot exceed 50 characters");
        }
        if (!normalized.matches("^[a-z]+(-[a-z]+)*$")) {
            throw new IllegalArgumentException("Username can only contain letters and hyphens, current: " + normalized);
        }
        this.username = normalized;
    }

    public String getValue() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserName other)) return false;
        return Objects.equals(username, other.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return username;
    }
}
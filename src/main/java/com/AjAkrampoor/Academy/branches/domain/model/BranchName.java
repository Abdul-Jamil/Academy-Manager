package com.AjAkrampoor.Academy.branches.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class BranchName {
    private String value;

    private BranchName() {
        // For JPA
    }

    public BranchName(String value) {
        if (value == null || value.trim().length() < 3) {
            throw new IllegalArgumentException("Branch name cannot be less than 3 letters");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("Branch name cannot be more than 100 letters");
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
        BranchName that = (BranchName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

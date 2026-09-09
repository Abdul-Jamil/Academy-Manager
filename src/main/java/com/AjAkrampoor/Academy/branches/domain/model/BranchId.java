package com.AjAkrampoor.Academy.branches.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class BranchId implements Serializable {
    private String id;

    private BranchId() {
    }

    public BranchId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static BranchId newId() {
        return new BranchId(UUID.randomUUID());
    }

    public static BranchId fromString(String id) {
        try {
            return new BranchId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid BranchId format: " + id + ". Must be a valid UUID.", e);
        }
    }
    
    public String asString() {
        return id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchId branchId = (BranchId) o;
        return Objects.equals(id, branchId.id);
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

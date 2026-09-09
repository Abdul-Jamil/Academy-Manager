package com.AjAkrampoor.Academy.inventory.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class SupplierName {

    private String value;

    protected SupplierName() {
    }

    public SupplierName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be empty");
        }

        if (value.length() > 200) {
            throw new IllegalArgumentException("Supplier name cannot exceed 150 characters");
        }

        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }
}

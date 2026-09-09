package com.AjAkrampoor.Academy.inventory.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductName {

    private String value;

    protected ProductName() {
    }

    public ProductName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if (value.length() > 150) {
            throw new IllegalArgumentException("Product name cannot exceed 150 characters");
        }

        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }
}

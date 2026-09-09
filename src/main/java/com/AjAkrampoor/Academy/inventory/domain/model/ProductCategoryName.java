package com.AjAkrampoor.Academy.inventory.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ProductCategoryName {

    private String value;

    protected ProductCategoryName() {
    }

    public ProductCategoryName(String value) {
        if (value == null || value.trim().length() < 3) {
            throw new IllegalArgumentException("Category name cannot be less than 3 letters");
        }

        if (value.length() > 100) {
            throw new IllegalArgumentException("Category name cannot be more than 100 letters");
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

        ProductCategoryName that = (ProductCategoryName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

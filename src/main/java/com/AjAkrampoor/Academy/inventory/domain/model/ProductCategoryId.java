package com.AjAkrampoor.Academy.inventory.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProductCategoryId implements Serializable {

    private String id;

    protected ProductCategoryId() {}

    public ProductCategoryId(UUID value) {
        this.id = String.valueOf(value);
    }

    public static ProductCategoryId newId() {
        return new ProductCategoryId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCategoryId that = (ProductCategoryId) o;
        return Objects.equals(id, that.id);
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

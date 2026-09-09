package com.AjAkrampoor.Academy.inventory.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Description;

import java.util.Objects;

public class ProductCategory {

    private ProductCategoryId categoryId;
    private ProductCategoryName categoryName;
    private Description description;
    private CategoryStatus categoryStatus;

    public ProductCategory(ProductCategoryId categoryId, ProductCategoryName categoryName, Description description, CategoryStatus categoryStatus) {
        this.categoryId = Objects.requireNonNull(categoryId, "CategoryId cannot be null");
        this.categoryName = Objects.requireNonNull(categoryName, "CategoryName cannot be null");
        this.description = description;
        this.categoryStatus = Objects.requireNonNull(categoryStatus, "CategoryStatus cannot be null");
    }

    public void updateName(ProductCategoryName categoryName) {
        if (categoryName == null) {
            throw new IllegalArgumentException("CategoryName cannot be null");
        }

        if (Objects.equals(categoryName.getValue(), this.categoryName.getValue())) {
            throw new IllegalArgumentException("New name is already in use");
        }

        this.categoryName = categoryName;
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public void activate() {
        this.categoryStatus = CategoryStatus.ACTIVE;
    }

    public void deactivate() {
        this.categoryStatus = CategoryStatus.INACTIVE;
    }

    public boolean isActive() {
        return categoryStatus == CategoryStatus.ACTIVE;
    }

    public ProductCategoryId getCategoryId() {
        return categoryId;
    }

    public ProductCategoryName getCategoryName() {
        return categoryName;
    }

    public Description getDescription() {
        return description;
    }

    public CategoryStatus getCategoryStatus() {
        return categoryStatus;
    }
}

package com.AjAkrampoor.Academy.income.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Description;

import java.util.Objects;

public class ProfitCategory {

    private ProfitCategoryId categoryId;
    private ProfitCategoryName categoryName;
    private Description description;
    private CategoryStatus categoryStatus;

    public ProfitCategory(
            ProfitCategoryId categoryId,
            ProfitCategoryName categoryName,
            Description description,
            CategoryStatus categoryStatus
    ) {
        this.categoryId = Objects.requireNonNull(
                categoryId,
                "Category Id cannot be null"
        );

        this.categoryName = Objects.requireNonNull(
                categoryName,
                "Category name cannot be null"
        );

        this.description = description;

        this.categoryStatus = Objects.requireNonNull(
                categoryStatus,
                "Category status cannot be null"
        );
    }

    public void updateName(ProfitCategoryName categoryName) {

        if (categoryName == null) {
            throw new IllegalArgumentException(
                    "Category name cannot be null"
            );
        }

        if (Objects.equals(
                categoryName.getValue(),
                this.categoryName.getValue()
        )) {
            throw new IllegalArgumentException(
                    "New name is already in use"
            );
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

    public ProfitCategoryId getCategoryId() {
        return categoryId;
    }

    public ProfitCategoryName getCategoryName() {
        return categoryName;
    }

    public Description getDescription() {
        return description;
    }

    public CategoryStatus getCategoryStatus() {
        return categoryStatus;
    }
}

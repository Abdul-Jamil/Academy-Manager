package com.AjAkrampoor.Academy.expenses.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Description;

import java.util.Objects;

public class ExpenseCategory {
    private ExpenseCategoryId categoryId;
    private ExpenseCategoryName categoryName;
    private Description description;
    private CategoryStatus categoryStatus;

    public ExpenseCategory(ExpenseCategoryId categoryId, ExpenseCategoryName categoryName, Description description, CategoryStatus categoryStatus) {
        this.categoryId = Objects.requireNonNull(categoryId, "Category Id cannot be null");
        this.categoryName = Objects.requireNonNull(categoryName, "Category name cannot be null");
        this.description = description;
        this.categoryStatus = Objects.requireNonNull(categoryStatus, "Category status cannot be null");
    }

    public void updateName(ExpenseCategoryName categoryName) {
        if (categoryName == null) {
            throw new IllegalArgumentException("categoryName cannot be null");
        }
        if (Objects.equals(categoryName.getValue(), this.categoryName.getValue())) {
            throw new IllegalArgumentException("New name is already in use");
        }
        this.categoryName = categoryName;
    }

    public void deactivate() {
        this.categoryStatus = CategoryStatus.INACTIVE;
    }

    public void activate() {
        this.categoryStatus = CategoryStatus.ACTIVE;
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public ExpenseCategoryId getCategoryId() {
        return categoryId;
    }

    public ExpenseCategoryName getCategoryName() {
        return categoryName;
    }

    public Description getDescription() {
        return description;
    }

    public CategoryStatus getCategoryStatus() {
        return categoryStatus;
    }

    public boolean isActive() {
        return categoryStatus == CategoryStatus.ACTIVE;
    }
}

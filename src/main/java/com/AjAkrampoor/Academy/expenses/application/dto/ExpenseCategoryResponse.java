package com.AjAkrampoor.Academy.expenses.application.dto;

import com.AjAkrampoor.Academy.expenses.domain.model.CategoryStatus;

public class ExpenseCategoryResponse {
    private String categoryId;
    private String categoryName;
    private String description;
    private CategoryStatus categoryStatus;

    public ExpenseCategoryResponse(String categoryId, String categoryName, String description, CategoryStatus categoryStatus) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.categoryStatus = categoryStatus;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    public CategoryStatus getCategoryStatus() {
        return categoryStatus;
    }
}

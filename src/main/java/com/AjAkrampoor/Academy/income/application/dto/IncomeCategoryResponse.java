package com.AjAkrampoor.Academy.income.application.dto;

import com.AjAkrampoor.Academy.income.domain.model.CategoryStatus;

public class IncomeCategoryResponse {
    private String categoryId;
    private String categoryName;
    private String description;
    private CategoryStatus categoryStatus;

    public IncomeCategoryResponse(String categoryId, String categoryName, String description, CategoryStatus categoryStatus) {
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

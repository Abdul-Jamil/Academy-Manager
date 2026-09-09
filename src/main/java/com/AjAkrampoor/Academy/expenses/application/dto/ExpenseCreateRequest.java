package com.AjAkrampoor.Academy.expenses.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class ExpenseCreateRequest {
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Amount cannot be less than 0")
    private BigDecimal amount;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    @NotNull(message = "Branch UUID is required")
    private UUID branchUUID;

    private String description;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getBranchUUID() {
        return branchUUID;
    }

    public void setBranchUUID(UUID branchUUID) {
        this.branchUUID = branchUUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

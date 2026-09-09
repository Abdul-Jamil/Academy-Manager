package com.AjAkrampoor.Academy.expenses.application.dto;

import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseResponse {
    private String expenseId;
    private String description;
    private BigDecimal amount;
    private String createdBy;
    private LocalDateTime createdAt;
    private String expenseCategoryName;
    private ExpenseStatus expenseStatus;
    private String deletedBy;
    private LocalDateTime deletedAt;
    private String BranchName;

    public ExpenseResponse(String expenseId, String description, BigDecimal amount, String createdBy, LocalDateTime createdAt, String expenseCategoryName, ExpenseStatus expenseStatus, String deletedBy, LocalDateTime deletedAt, String BranchName) {
        this.expenseId = expenseId;
        this.description = description;
        this.amount = amount;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.expenseCategoryName = expenseCategoryName;
        this.expenseStatus = expenseStatus;
        this.deletedBy = deletedBy;
        this.deletedAt = deletedAt;
        this.BranchName = BranchName;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getExpenseCategoryName() {
        return expenseCategoryName;
    }

    public ExpenseStatus getExpenseStatus() {
        return expenseStatus;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public String getBranchName() {
        return BranchName;
    }
}

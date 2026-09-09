package com.AjAkrampoor.Academy.expenses.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Expense {
    private ExpenseId expenseId;
    private Description description;
    private Money amount;
    private UserId createdBy;
    private LocalDateTime createdAt;
    private ExpenseCategoryId expenseCategoryId;
    private ExpenseStatus expenseStatus;
    private UserId deletedBy;
    private LocalDateTime deletedAt;
    private BranchId branchId;

    public Expense(ExpenseId expenseId, Description description, Money amount, UserId createdBy, LocalDateTime createdAt, ExpenseCategoryId expenseCategoryId, ExpenseStatus expenseStatus, UserId deletedBy, LocalDateTime deletedAt, BranchId branchId) {
        this.expenseId = Objects.requireNonNull(expenseId, "ExpenseId cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.createdBy = Objects.requireNonNull(createdBy, "CreatedBy cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt cannot be null");
        this.expenseCategoryId = Objects.requireNonNull(expenseCategoryId, "ExpenseCategoryId cannot be null");
        this.expenseStatus = Objects.requireNonNull(expenseStatus, "ExpenseStatus cannot be null");
        this.deletedBy = deletedBy;
        this.deletedAt = deletedAt;
        this.branchId = Objects.requireNonNull(branchId, "BranchId cannot be null");
    }

    public void updateDescription(Description description) {
        if (this.description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }

    public void delete(UserId deletedBy) {
        if (this.expenseStatus == ExpenseStatus.INACTIVE) {
            throw new IllegalStateException("Expense is already deleted.");
        }
        this.deletedBy = deletedBy;
        this.deletedAt = LocalDateTime.now();
        this.expenseStatus = ExpenseStatus.INACTIVE;
    }

    public void updateCategory(ExpenseCategoryId expenseCategoryId) {
        if (expenseCategoryId == null) {
            throw new IllegalArgumentException("ExpenseCategoryId cannot be null");
        }
        this.expenseCategoryId = expenseCategoryId;
    }


    public ExpenseId getExpenseId() {
        return expenseId;
    }

    public Description getDescription() {
        return description;
    }

    public Money getAmount() {
        return amount;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ExpenseCategoryId getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public ExpenseStatus getExpenseStatus() {
        return expenseStatus;
    }

    public boolean isDeleted() {
        return this.expenseStatus == ExpenseStatus.INACTIVE;
    }

    public UserId getDeletedBy() {
        return deletedBy;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public BranchId getBranchId() {
        return branchId;
    }
}

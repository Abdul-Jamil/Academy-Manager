package com.AjAkrampoor.Academy.expenses.infrastruture.persistence;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class ExpenseJpaEntity {
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "expense_id", nullable = false, updatable = false, unique = true))
    private ExpenseId Id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description", nullable = false))
    private Description description;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "amount", nullable = false, updatable = false))
    private Money amount;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "created_by", nullable = false, updatable = false))
    private UserId createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "category_id", nullable = false, updatable = false))
    private ExpenseCategoryId expenseCategoryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseStatus expenseStatus;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "deleted_by"))
    private UserId deletedBy;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "branch_id", nullable = false, updatable = false))
    private BranchId branchId;


    private LocalDateTime deletedAt;

    public ExpenseId getId() {
        return Id;
    }

    public void setId(ExpenseId id) {
        Id = id;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserId createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ExpenseCategoryId getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public void setExpenseCategoryId(ExpenseCategoryId expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    public ExpenseStatus getExpenseStatus() {
        return expenseStatus;
    }

    public void setExpenseStatus(ExpenseStatus expenseStatus) {
        this.expenseStatus = expenseStatus;
    }

    public UserId getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(UserId deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchId branchId) {
        this.branchId = branchId;
    }
}

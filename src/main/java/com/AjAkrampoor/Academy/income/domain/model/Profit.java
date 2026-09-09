package com.AjAkrampoor.Academy.income.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Profit {

    private ProfitId profitId;
    private Description description;
    private Money amount;
    private UserId createdBy;
    private LocalDateTime createdAt;
    private ProfitCategoryId profitCategoryId;
    private ProfitStatus profitStatus;
    private UserId deletedBy;
    private LocalDateTime deletedAt;
    private BranchId branchId;

    public Profit(ProfitId profitId, Description description, Money amount, UserId createdBy, LocalDateTime createdAt, ProfitCategoryId profitCategoryId, ProfitStatus profitStatus, UserId deletedBy, LocalDateTime deletedAt, BranchId branchId
    ) {
        this.profitId = Objects.requireNonNull(profitId, "ProfitId cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.createdBy = Objects.requireNonNull(createdBy, "CreatedBy cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt cannot be null");
        this.profitCategoryId = Objects.requireNonNull(profitCategoryId, "ProfitCategoryId cannot be null");
        this.profitStatus = Objects.requireNonNull(profitStatus, "ProfitStatus cannot be null");
        this.deletedBy = deletedBy;
        this.deletedAt = deletedAt;
        this.branchId = Objects.requireNonNull(branchId, "BranchId cannot be null");
    }

    public void updateDescription(Description description) {

        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }

        this.description = description;
    }

    public void updateCategory(ProfitCategoryId profitCategoryId) {

        if (profitCategoryId == null) {
            throw new IllegalArgumentException("ProfitCategoryId cannot be null");
        }

        this.profitCategoryId = profitCategoryId;
    }

    public void delete(UserId deletedBy) {

        if (this.profitStatus == ProfitStatus.INACTIVE) {
            throw new IllegalStateException("Profit is already deleted.");
        }

        this.deletedBy = Objects.requireNonNull(deletedBy, "DeletedBy cannot be null");

        this.deletedAt = LocalDateTime.now();
        this.profitStatus = ProfitStatus.INACTIVE;
    }

    public void activate() {
        if (this.profitStatus == ProfitStatus.ACTIVE) {
            return;
        }
        this.profitStatus = ProfitStatus.ACTIVE;
        this.deletedBy = null;
        this.deletedAt = null;
    }

    public ProfitId getProfitId() {
        return profitId;
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

    public ProfitCategoryId getProfitCategoryId() {
        return profitCategoryId;
    }

    public ProfitStatus getProfitStatus() {
        return profitStatus;
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

    public boolean isDeleted() {
        return this.profitStatus == ProfitStatus.INACTIVE;
    }
}

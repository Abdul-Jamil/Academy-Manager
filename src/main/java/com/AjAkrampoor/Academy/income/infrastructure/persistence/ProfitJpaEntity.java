package com.AjAkrampoor.Academy.income.infrastructure.persistence;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profits")
public class ProfitJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "profit_id",
                    nullable = false,
                    updatable = false,
                    unique = true
            )
    )
    private ProfitId profitId;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(
                    name = "description",
                    nullable = false
            )
    )
    private Description description;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(
                    name = "amount",
                    nullable = false,
                    updatable = false
            )
    )
    private Money amount;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "created_by",
                    nullable = false,
                    updatable = false
            )
    )
    private UserId createdBy;

    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "category_id",
                    nullable = false,
                    updatable = false
            )
    )
    private ProfitCategoryId profitCategoryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfitStatus profitStatus;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "deleted_by")
    )
    private UserId deletedBy;

    private LocalDateTime deletedAt;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "branch_id",
                    nullable = false,
                    updatable = false
            )
    )
    private BranchId branchId;

    public ProfitJpaEntity() {
        // For JPA
    }

    public ProfitId getProfitId() {
        return profitId;
    }

    public void setProfitId(ProfitId profitId) {
        this.profitId = profitId;
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

    public ProfitCategoryId getProfitCategoryId() {
        return profitCategoryId;
    }

    public void setProfitCategoryId(ProfitCategoryId profitCategoryId) {
        this.profitCategoryId = profitCategoryId;
    }

    public ProfitStatus getProfitStatus() {
        return profitStatus;
    }

    public void setProfitStatus(ProfitStatus profitStatus) {
        this.profitStatus = profitStatus;
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

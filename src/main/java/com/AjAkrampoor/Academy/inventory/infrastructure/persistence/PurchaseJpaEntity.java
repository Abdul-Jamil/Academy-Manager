package com.AjAkrampoor.Academy.inventory.infrastructure.persistence;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseStatus;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
public class PurchaseJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "purchase_id",
                    nullable = false,
                    updatable = false,
                    unique = true
            )
    )
    private PurchaseId purchaseId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "supplier_id"
            )
    )
    private SupplierId supplierId;

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

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(
                    name = "total_amount",
                    nullable = false
            )
    )
    private Money totalAmount;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "description")
    )
    private Description description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime purchasedAt;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseStatus purchaseStatus;

    public PurchaseJpaEntity() {
    }

    public PurchaseId getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(PurchaseId purchaseId) {
        this.purchaseId = purchaseId;
    }

    public SupplierId getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(SupplierId supplierId) {
        this.supplierId = supplierId;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchId branchId) {
        this.branchId = branchId;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserId createdBy) {
        this.createdBy = createdBy;
    }

    public PurchaseStatus getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(PurchaseStatus purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }
}

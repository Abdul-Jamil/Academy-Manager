package com.AjAkrampoor.Academy.inventory.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Purchase {

    private PurchaseId purchaseId;
    private SupplierId supplierId;
    private BranchId branchId;
    private Money totalAmount;
    private Description description;
    private LocalDateTime purchasedAt;
    private UserId createdBy;
    private PurchaseStatus purchaseStatus;

    public Purchase(PurchaseId purchaseId, SupplierId supplierId, BranchId branchId, Money totalAmount, Description description, LocalDateTime purchasedAt, UserId createdBy, PurchaseStatus purchaseStatus) {
        this.purchaseId = Objects.requireNonNull(purchaseId, "purchaseId cannot be null");
        this.supplierId = supplierId;
        this.branchId = Objects.requireNonNull(branchId, "branchId cannot be null");
        this.totalAmount = Objects.requireNonNull(totalAmount, "totalAmount cannot be null");
        this.description = description;
        this.purchasedAt = Objects.requireNonNull(purchasedAt, "purchasedAt cannot be null");
        this.createdBy = Objects.requireNonNull(createdBy, "createdBy cannot be null");
        this.purchaseStatus = Objects.requireNonNull(purchaseStatus, "purchaseStatus cannot be null");
    }

    public void cancel() {
        if (purchaseStatus == PurchaseStatus.CANCELLED) {
            throw new IllegalStateException("Purchase is already cancelled");
        }
        purchaseStatus = PurchaseStatus.CANCELLED;
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public PurchaseId getPurchaseId() {
        return purchaseId;
    }

    public SupplierId getSupplierId() {
        return supplierId;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public Description getDescription() {
        return description;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public PurchaseStatus getPurchaseStatus() {
        return purchaseStatus;
    }
}

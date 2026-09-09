package com.AjAkrampoor.Academy.inventory.application.dto;

import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseResponse {
    private String purchaseId;
    private String supplierName;
    private String branchName;
    private BigDecimal totalAmount;
    private String description;
    private LocalDateTime purchasedAt;
    private String createdBy;
    private PurchaseStatus purchaseStatus;

    public PurchaseResponse(String purchaseId, String supplierName, String branchName, BigDecimal totalAmount,
                            String description, LocalDateTime purchasedAt, String createdBy,
                            PurchaseStatus purchaseStatus) {
        this.purchaseId = purchaseId;
        this.supplierName = supplierName;
        this.branchName = branchName;
        this.totalAmount = totalAmount;
        this.description = description;
        this.purchasedAt = purchasedAt;
        this.createdBy = createdBy;
        this.purchaseStatus = purchaseStatus;
    }

    // Getters
    public String getPurchaseId() {
        return purchaseId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getBranchName() {
        return branchName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PurchaseStatus getPurchaseStatus() {
        return purchaseStatus;
    }
}

package com.AjAkrampoor.Academy.inventory.application.dto;

import com.AjAkrampoor.Academy.inventory.domain.model.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaleResponse {
    private String saleId;
    private String branchName;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private String soldBy;
    private LocalDateTime soldAt;
    private String description;
    private SaleStatus saleStatus;

    public SaleResponse(String saleId, String branchName, BigDecimal totalAmount, BigDecimal discountAmount,
                        String soldBy, LocalDateTime soldAt, String description, SaleStatus saleStatus) {
        this.saleId = saleId;
        this.branchName = branchName;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.soldBy = soldBy;
        this.soldAt = soldAt;
        this.description = description;
        this.saleStatus = saleStatus;
    }

    // Getters
    public String getSaleId() {
        return saleId;
    }

    public String getBranchName() {
        return branchName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public String getDescription() {
        return description;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }
}

package com.AjAkrampoor.Academy.inventory.application.dto;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse {
    private String productId;
    private String productName;
    private String description;
    private String categoryName;
    private BigDecimal sellingPrice;
    private Long quantity;
    private ProductStatus productStatus;
    private String createdBy;
    private LocalDateTime createdAt;
    private String branchName;

    public ProductResponse(String productId, String productName, String description, String categoryName,
                           BigDecimal sellingPrice, Long quantity, ProductStatus productStatus,
                           String createdBy, LocalDateTime createdAt, String branchName) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.categoryName = categoryName;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.productStatus = productStatus;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.branchName = branchName;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getBranchName() {
        return branchName;
    }
}

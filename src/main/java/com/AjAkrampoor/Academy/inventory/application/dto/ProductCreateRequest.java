package com.AjAkrampoor.Academy.inventory.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreateRequest {
    @NotBlank(message = "Product name is required")
    private String productName;

    private String description;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Selling price cannot be negative")
    private BigDecimal sellingPrice;

    @Min(value = 0, message = "Quantity must be at least 0")
    private Long quantity;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    private UUID branchUUID;

    // Getters and setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getBranchUUID() {
        return branchUUID;
    }

    public void setBranchUUID(UUID branchUUID) {
        this.branchUUID = branchUUID;
    }
}

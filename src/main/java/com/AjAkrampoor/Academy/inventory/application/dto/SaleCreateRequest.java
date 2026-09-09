package com.AjAkrampoor.Academy.inventory.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class SaleCreateRequest {
    @NotEmpty(message = "At least one item is required")
    @Valid
    private List<SaleItemRequest> items;

    @DecimalMin(value = "0.00", inclusive = true, message = "Discount cannot be negative")
    private BigDecimal discountAmount;

    private String description;
    private UUID branchUUID; // optional for super admin

    public List<SaleItemRequest> getItems() {
        return items;
    }

    public void setItems(List<SaleItemRequest> items) {
        this.items = items;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getBranchUUID() {
        return branchUUID;
    }

    public void setBranchUUID(UUID branchUUID) {
        this.branchUUID = branchUUID;
    }

    public static class SaleItemRequest {
        @NotNull(message = "Product ID is required")
        private UUID productId;

        @Min(value = 1, message = "Quantity must be at least 1")
        private long quantity;

        // Getters and setters
        public UUID getProductId() {
            return productId;
        }

        public void setProductId(UUID productId) {
            this.productId = productId;
        }

        public long getQuantity() {
            return quantity;
        }

        public void setQuantity(long quantity) {
            this.quantity = quantity;
        }
    }
}

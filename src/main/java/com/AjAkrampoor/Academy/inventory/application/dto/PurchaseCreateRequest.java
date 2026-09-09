package com.AjAkrampoor.Academy.inventory.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PurchaseCreateRequest {
    private UUID supplierId;

    @NotEmpty(message = "At least one item is required")
    @Valid
    private List<PurchaseItemRequest> items;

    private String description;
    private UUID branchUUID;

    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
    }

    public List<PurchaseItemRequest> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItemRequest> items) {
        this.items = items;
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

    public static class PurchaseItemRequest {
        @NotNull(message = "Product ID is required")
        private UUID productId;

        @Min(value = 1, message = "Quantity must be at least 1")
        private long quantity;

        @NotNull(message = "Unit cost is required")
        @DecimalMin(value = "0.00", inclusive = true, message = "Unit cost cannot be negative")
        private BigDecimal unitCost;

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

        public BigDecimal getUnitCost() {
            return unitCost;
        }

        public void setUnitCost(BigDecimal unitCost) {
            this.unitCost = unitCost;
        }
    }
}

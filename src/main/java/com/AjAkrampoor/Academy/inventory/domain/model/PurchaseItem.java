package com.AjAkrampoor.Academy.inventory.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Money;

import java.util.Objects;

public class PurchaseItem {

    private PurchaseItemId purchaseItemId;
    private PurchaseId purchaseId;
    private ProductId productId;
    private int quantity;
    private Money unitCost;
    private Money totalCost;

    public PurchaseItem(PurchaseItemId purchaseItemId, PurchaseId purchaseId, ProductId productId, int quantity, Money unitCost, Money totalCost) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        this.purchaseItemId = Objects.requireNonNull(purchaseItemId, "purchaseItemId cannot be null");
        this.purchaseId = Objects.requireNonNull(purchaseId, "purchaseId cannot be null");
        this.productId = Objects.requireNonNull(productId, "productId cannot be null");
        this.quantity = quantity;
        this.unitCost = Objects.requireNonNull(unitCost, "unitCost cannot be null");
        this.totalCost = Objects.requireNonNull(totalCost, "totalCost cannot be null");
    }

    public PurchaseItemId getPurchaseItemId() {
        return purchaseItemId;
    }

    public PurchaseId getPurchaseId() {
        return purchaseId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getUnitCost() {
        return unitCost;
    }

    public Money getTotalCost() {
        return totalCost;
    }
}

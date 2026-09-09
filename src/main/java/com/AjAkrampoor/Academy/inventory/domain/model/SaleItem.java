package com.AjAkrampoor.Academy.inventory.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Money;

import java.util.Objects;

public class SaleItem {

    private SaleItemId saleItemId;
    private SaleId saleId;
    private ProductId productId;
    private int quantity;
    private Money unitPrice;
    private Money totalAmount;

    public SaleItem(SaleItemId saleItemId, SaleId saleId, ProductId productId, int quantity, Money unitPrice, Money totalAmount) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        this.saleItemId = Objects.requireNonNull(saleItemId, "saleItemId cannot be null");
        this.saleId = Objects.requireNonNull(saleId, "saleId cannot be null");
        this.productId = Objects.requireNonNull(productId, "productId cannot be null");
        this.quantity = quantity;
        this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice cannot be null");
        this.totalAmount = Objects.requireNonNull(totalAmount, "totalAmount cannot be null");
    }

    public void assignToSale(SaleId saleId) {
        this.saleId = Objects.requireNonNull(saleId, "SaleId cannot be null");
    }

    public SaleItemId getSaleItemId() {
        return saleItemId;
    }

    public SaleId getSaleId() {
        return saleId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }
}

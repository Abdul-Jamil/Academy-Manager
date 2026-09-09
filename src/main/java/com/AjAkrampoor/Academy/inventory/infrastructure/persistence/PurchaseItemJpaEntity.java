package com.AjAkrampoor.Academy.inventory.infrastructure.persistence;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductId;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseItemId;
import com.AjAkrampoor.Academy.shared.vo.Money;
import jakarta.persistence.*;

@Entity
@Table(name = "purchase_items")
public class PurchaseItemJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "purchase_item_id",
                    nullable = false,
                    updatable = false,
                    unique = true
            )
    )
    private PurchaseItemId purchaseItemId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "purchase_id",
                    nullable = false,
                    updatable = false
            )
    )
    private PurchaseId purchaseId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "product_id",
                    nullable = false,
                    updatable = false
            )
    )
    private ProductId productId;

    @Column(nullable = false)
    private int quantity;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(
                    name = "unit_cost",
                    nullable = false
            )
    )
    private Money unitCost;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(
                    name = "total_cost",
                    nullable = false
            )
    )
    private Money totalCost;

    public PurchaseItemJpaEntity() {
    }

    public PurchaseItemId getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(PurchaseItemId purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public PurchaseId getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(PurchaseId purchaseId) {
        this.purchaseId = purchaseId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Money getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Money unitCost) {
        this.unitCost = unitCost;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Money totalCost) {
        this.totalCost = totalCost;
    }
}

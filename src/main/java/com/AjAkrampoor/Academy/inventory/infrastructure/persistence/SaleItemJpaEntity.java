package com.AjAkrampoor.Academy.inventory.infrastructure.persistence;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductId;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleId;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleItemId;
import com.AjAkrampoor.Academy.shared.vo.Money;
import jakarta.persistence.*;

@Entity
@Table(name = "sale_items")
public class SaleItemJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "sale_item_id",
                    nullable = false,
                    updatable = false,
                    unique = true
            )
    )
    private SaleItemId saleItemId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "sale_id",
                    nullable = false,
                    updatable = false
            )
    )
    private SaleId saleId;

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
                    name = "unit_price",
                    nullable = false
            )
    )
    private Money unitPrice;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(
                    name = "total_amount",
                    nullable = false
            )
    )
    private Money totalAmount;

    public SaleItemJpaEntity() {
    }

    public SaleItemId getSaleItemId() {
        return saleItemId;
    }

    public void setSaleItemId(SaleItemId saleItemId) {
        this.saleItemId = saleItemId;
    }

    public SaleId getSaleId() {
        return saleId;
    }

    public void setSaleId(SaleId saleId) {
        this.saleId = saleId;
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

    public Money getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Money unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }
}

package com.AjAkrampoor.Academy.inventory.infrastructure.persistence;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductName;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "product_id",
                    nullable = false,
                    updatable = false,
                    unique = true
            )
    )
    private ProductId productId;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(
                    name = "product_name",
                    nullable = false
            )
    )
    private ProductName productName;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "description")
    )
    private Description description;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "category_id",
                    nullable = false
            )
    )
    private ProductCategoryId categoryId;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(
                    name = "selling_price",
                    nullable = false
            )
    )
    private Money sellingPrice;

    @Column(nullable = true)
    private Long quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus productStatus;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "created_by",
                    nullable = false,
                    updatable = false
            )
    )
    private UserId createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "branch_id",
                    nullable = false,
                    updatable = false
            )
    )
    private BranchId branchId;

    public ProductJpaEntity() {
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public ProductName getProductName() {
        return productName;
    }

    public void setProductName(ProductName productName) {
        this.productName = productName;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public ProductCategoryId getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ProductCategoryId categoryId) {
        this.categoryId = categoryId;
    }

    public Money getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Money sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserId createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchId branchId) {
        this.branchId = branchId;
    }
}

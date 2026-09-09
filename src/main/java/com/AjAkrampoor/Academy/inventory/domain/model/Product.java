package com.AjAkrampoor.Academy.inventory.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {

    private ProductId productId;
    private ProductName productName;
    private Description description;
    private ProductCategoryId categoryId;
    private Money sellingPrice;
    private Long quantity;
    private ProductStatus productStatus;
    private UserId createdBy;
    private LocalDateTime createdAt;
    private BranchId branchId;

    public Product(ProductId productId, ProductName productName, Description description, ProductCategoryId categoryId, Money sellingPrice, Long quantity, ProductStatus productStatus, UserId createdBy, LocalDateTime createdAt, BranchId branchId
    ) {
        this.productId = Objects.requireNonNull(productId);
        this.productName = Objects.requireNonNull(productName);
        this.description = description;
        this.categoryId = Objects.requireNonNull(categoryId);
        this.sellingPrice = Objects.requireNonNull(sellingPrice);
        this.quantity = quantity;
        this.productStatus = Objects.requireNonNull(productStatus);
        this.createdBy = Objects.requireNonNull(createdBy);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.branchId = Objects.requireNonNull(branchId);
    }

    public void updateName(ProductName productName) {
        this.productName = Objects.requireNonNull(productName);
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public void updateCategory(ProductCategoryId categoryId) {
        this.categoryId = Objects.requireNonNull(categoryId);
    }

    public void updateSellingPrice(Money sellingPrice) {
        this.sellingPrice = Objects.requireNonNull(sellingPrice);
    }

    public void increaseQuantity(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Increase amount must be positive");
        }

        this.quantity = (this.quantity == null ? 0L : this.quantity) + amount;
    }

    public void decreaseQuantity(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Decrease amount must be positive");
        }
        if (this.quantity == null) {
            throw new IllegalStateException("Quantity has not been set");
        }
        if (this.quantity < amount) {
            throw new IllegalArgumentException("Insufficient quantity: requested " + amount + ", available " + this.quantity);
        }
        this.quantity -= amount;
    }

    public void deactivate() {
        this.productStatus = ProductStatus.INACTIVE;
    }

    public void activate() {
        this.productStatus = ProductStatus.ACTIVE;
    }

    public boolean isActive() {
        return productStatus == ProductStatus.ACTIVE;
    }

    public ProductId getProductId() {
        return productId;
    }

    public ProductName getProductName() {
        return productName;
    }

    public Description getDescription() {
        return description;
    }

    public ProductCategoryId getCategoryId() {
        return categoryId;
    }

    public Money getSellingPrice() {
        return sellingPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BranchId getBranchId() {
        return branchId;
    }
}

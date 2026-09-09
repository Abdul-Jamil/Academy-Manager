package com.AjAkrampoor.Academy.inventory.infrastructure.persistence;

import com.AjAkrampoor.Academy.inventory.domain.model.CategoryStatus;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryName;
import com.AjAkrampoor.Academy.shared.vo.Description;
import jakarta.persistence.*;

@Entity
@Table(name = "product_categories")
public class ProductCategoryJpaEntity {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "category_id", nullable = false, updatable = false, unique = true))
    private ProductCategoryId categoryId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category_name", nullable = false, unique = true))
    private ProductCategoryName categoryName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private Description description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus categoryStatus;

    public ProductCategoryJpaEntity() {
    }

    public ProductCategoryId getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ProductCategoryId categoryId) {
        this.categoryId = categoryId;
    }

    public ProductCategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(ProductCategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public CategoryStatus getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(CategoryStatus categoryStatus) {
        this.categoryStatus = categoryStatus;
    }
}

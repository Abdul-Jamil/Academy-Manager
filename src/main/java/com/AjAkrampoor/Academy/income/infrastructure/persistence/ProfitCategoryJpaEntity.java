package com.AjAkrampoor.Academy.income.infrastructure.persistence;

import com.AjAkrampoor.Academy.income.domain.model.CategoryStatus;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryName;
import com.AjAkrampoor.Academy.shared.vo.Description;
import jakarta.persistence.*;

@Entity
@Table(name = "profit_categories")
public class ProfitCategoryJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "category_id",
                    nullable = false,
                    updatable = false,
                    unique = true
            )
    )
    private ProfitCategoryId categoryId;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(
                    name = "category_name",
                    nullable = false,
                    unique = true
            )
    )
    private ProfitCategoryName categoryName;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "description")
    )
    private Description description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus categoryStatus;

    public ProfitCategoryJpaEntity() {
        // For JPA
    }

    public ProfitCategoryId getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ProfitCategoryId categoryId) {
        this.categoryId = categoryId;
    }

    public ProfitCategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(ProfitCategoryName categoryName) {
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

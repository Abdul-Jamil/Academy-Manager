package com.AjAkrampoor.Academy.expenses.infrastruture.persistence;

import com.AjAkrampoor.Academy.expenses.domain.model.CategoryStatus;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryName;
import com.AjAkrampoor.Academy.shared.vo.Description;
import jakarta.persistence.*;

@Entity
@Table(name = "expense_category")
public class ExpenseCategoryJpaEntity {
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "category_id", nullable = false, unique = true))
    private ExpenseCategoryId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false, unique = true))
    private ExpenseCategoryName name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "note"))
    private Description description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus categoryStatus;

    public ExpenseCategoryId getId() {
        return id;
    }

    public void setId(ExpenseCategoryId id) {
        this.id = id;
    }

    public ExpenseCategoryName getName() {
        return name;
    }

    public void setName(ExpenseCategoryName name) {
        this.name = name;
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

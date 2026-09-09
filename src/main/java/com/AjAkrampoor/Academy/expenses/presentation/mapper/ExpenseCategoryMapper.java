package com.AjAkrampoor.Academy.expenses.presentation.mapper;

import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
import com.AjAkrampoor.Academy.expenses.infrastruture.persistence.ExpenseCategoryJpaEntity;

import java.util.List;

public class ExpenseCategoryMapper {

    public static ExpenseCategory toDomain(ExpenseCategoryJpaEntity entity) {
        return new ExpenseCategory
                (
                        entity.getId(),
                        entity.getName(),
                        entity.getDescription(),
                        entity.getCategoryStatus()
                );
    }

    public static ExpenseCategoryJpaEntity toEntity(ExpenseCategory domain) {
        ExpenseCategoryJpaEntity entity = new ExpenseCategoryJpaEntity();

        entity.setId(domain.getCategoryId());
        entity.setName(domain.getCategoryName());
        entity.setDescription(domain.getDescription());
        entity.setCategoryStatus(domain.getCategoryStatus());
        return entity;
    }

    public static List<ExpenseCategory> toDomain(List<ExpenseCategoryJpaEntity> entities) {
        return entities.stream()
                .map(ExpenseCategoryMapper::toDomain)
                .toList();
    }

    public static List<ExpenseCategoryJpaEntity> toEntity(List<ExpenseCategory> domains) {
        return domains.stream()
                .map(ExpenseCategoryMapper::toEntity)
                .toList();
    }
}

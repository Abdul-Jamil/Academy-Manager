package com.AjAkrampoor.Academy.expenses.presentation.mapper;

import com.AjAkrampoor.Academy.expenses.domain.model.Expense;
import com.AjAkrampoor.Academy.expenses.infrastruture.persistence.ExpenseJpaEntity;

import java.util.List;

public class ExpenseMapper {

    public static Expense toDomain(ExpenseJpaEntity entity) {
        return new Expense
                (
                        entity.getId(),
                        entity.getDescription(),
                        entity.getAmount(),
                        entity.getCreatedBy(),
                        entity.getCreatedAt(),
                        entity.getExpenseCategoryId(),
                        entity.getExpenseStatus(),
                        entity.getDeletedBy(),
                        entity.getDeletedAt(),
                        entity.getBranchId()
                );
    }

    public static ExpenseJpaEntity toEntity(Expense domain) {
        ExpenseJpaEntity entity = new ExpenseJpaEntity();

        entity.setId(domain.getExpenseId());
        entity.setDescription(domain.getDescription());
        entity.setAmount(domain.getAmount());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setExpenseCategoryId(domain.getExpenseCategoryId());
        entity.setExpenseStatus(domain.getExpenseStatus());
        entity.setDeletedBy(domain.getDeletedBy());
        entity.setDeletedAt(domain.getDeletedAt());
        entity.setBranchId(domain.getBranchId());
        return entity;
    }

    public List<Expense> toDomainList(List<ExpenseJpaEntity> entities) {
        return entities.stream()
                .map(ExpenseMapper::toDomain)
                .toList();
    }

    public List<ExpenseJpaEntity> toEntityList(List<Expense> domains) {
        return domains.stream()
                .map(ExpenseMapper::toEntity)
                .toList();
    }
}

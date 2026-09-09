package com.AjAkrampoor.Academy.income.presentation.mapper;

import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import com.AjAkrampoor.Academy.income.infrastructure.persistence.ProfitCategoryJpaEntity;

import java.util.List;

public class ProfitCategoryMapper {

    public static ProfitCategory toDomain(ProfitCategoryJpaEntity entity) {
        return new ProfitCategory(
                entity.getCategoryId(),
                entity.getCategoryName(),
                entity.getDescription(),
                entity.getCategoryStatus()
        );
    }

    public static ProfitCategoryJpaEntity toEntity(ProfitCategory domain) {
        ProfitCategoryJpaEntity entity = new ProfitCategoryJpaEntity();
        entity.setCategoryId(domain.getCategoryId());
        entity.setCategoryName(domain.getCategoryName());
        entity.setDescription(domain.getDescription());
        entity.setCategoryStatus(domain.getCategoryStatus());
        return entity;
    }

    public static List<ProfitCategory> toDomain(List<ProfitCategoryJpaEntity> entities) {
        return entities.stream().map(ProfitCategoryMapper::toDomain).toList();
    }

    public static List<ProfitCategoryJpaEntity> toEntity(List<ProfitCategory> domains) {
        return domains.stream().map(ProfitCategoryMapper::toEntity).toList();
    }
}

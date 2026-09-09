package com.AjAkrampoor.Academy.inventory.presentation.mapper;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.ProductCategoryJpaEntity;

import java.util.List;

public class ProductCategoryMapper {

    public static ProductCategory toDomain(ProductCategoryJpaEntity entity) {
        return new ProductCategory
                (
                        entity.getCategoryId(),
                        entity.getCategoryName(),
                        entity.getDescription(),
                        entity.getCategoryStatus()
                );
    }

    public static ProductCategoryJpaEntity toEntity(ProductCategory domain) {
        ProductCategoryJpaEntity entity = new ProductCategoryJpaEntity();

        entity.setCategoryId(domain.getCategoryId());
        entity.setCategoryName(domain.getCategoryName());
        entity.setDescription(domain.getDescription());
        entity.setCategoryStatus(domain.getCategoryStatus());
        return entity;
    }

    public static List<ProductCategory> toDomainList(List<ProductCategoryJpaEntity> entities) {
        return entities.stream()
                .map(ProductCategoryMapper::toDomain)
                .toList();
    }

    public static List<ProductCategoryJpaEntity> toEntityList(List<ProductCategory> domains) {
        return domains.stream()
                .map(ProductCategoryMapper::toEntity)
                .toList();
    }
}

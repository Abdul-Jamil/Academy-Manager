package com.AjAkrampoor.Academy.inventory.presentation.mapper;

import com.AjAkrampoor.Academy.inventory.domain.model.Product;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.ProductJpaEntity;

import java.util.List;

public class ProductMapper {

    public static Product toDomain(ProductJpaEntity entity) {
        return new Product
                (
                        entity.getProductId(),
                        entity.getProductName(),
                        entity.getDescription(),
                        entity.getCategoryId(),
                        entity.getSellingPrice(),
                        entity.getQuantity(),
                        entity.getProductStatus(),
                        entity.getCreatedBy(),
                        entity.getCreatedAt(),
                        entity.getBranchId()
                );
    }

    public static ProductJpaEntity toEntity(Product domain) {
        ProductJpaEntity productJpaEntity = new ProductJpaEntity();
        productJpaEntity.setProductId(domain.getProductId());
        productJpaEntity.setProductName(domain.getProductName());
        productJpaEntity.setDescription(domain.getDescription());
        productJpaEntity.setCategoryId(domain.getCategoryId());
        productJpaEntity.setSellingPrice(domain.getSellingPrice());
        productJpaEntity.setQuantity(domain.getQuantity());
        productJpaEntity.setProductStatus(domain.getProductStatus());
        productJpaEntity.setCreatedBy(domain.getCreatedBy());
        productJpaEntity.setCreatedAt(domain.getCreatedAt());
        productJpaEntity.setBranchId(domain.getBranchId());
        return productJpaEntity;
    }

    public static List<Product> toDomainList(List<ProductJpaEntity> entities) {
        return entities.stream()
                .map(ProductMapper::toDomain)
                .toList();
    }

    public static List<ProductJpaEntity> toEntityList(List<Product> domains) {
        return domains.stream()
                .map(ProductMapper::toEntity)
                .toList();
    }
}

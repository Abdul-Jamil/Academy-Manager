package com.AjAkrampoor.Academy.inventory.presentation.mapper;

import com.AjAkrampoor.Academy.inventory.domain.model.SaleItem;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.SaleItemJpaEntity;

import java.util.List;

public class SaleItemMapper {

    public static SaleItem toDomain(SaleItemJpaEntity entity) {
        return new SaleItem
                (
                        entity.getSaleItemId(),
                        entity.getSaleId(),
                        entity.getProductId(),
                        entity.getQuantity(),
                        entity.getUnitPrice(),
                        entity.getTotalAmount()
                );
    }

    public static SaleItemJpaEntity toEntity(SaleItem domain) {
        SaleItemJpaEntity entity = new SaleItemJpaEntity();

        entity.setSaleItemId(domain.getSaleItemId());
        entity.setSaleId(domain.getSaleId());
        entity.setProductId(domain.getProductId());
        entity.setQuantity(domain.getQuantity());
        entity.setUnitPrice(domain.getUnitPrice());
        entity.setTotalAmount(domain.getTotalAmount());
        return entity;
    }

    public static List<SaleItem> toDomain(List<SaleItemJpaEntity> entities) {
        return entities.stream()
                .map(SaleItemMapper::toDomain)
                .toList();
    }

    public static List<SaleItemJpaEntity> toEntity(List<SaleItem> domains) {
        return domains.stream()
                .map(SaleItemMapper::toEntity)
                .toList();
    }
}

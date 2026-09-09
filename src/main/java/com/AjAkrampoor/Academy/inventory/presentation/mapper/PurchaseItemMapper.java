package com.AjAkrampoor.Academy.inventory.presentation.mapper;

import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseItem;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.PurchaseItemJpaEntity;

import java.util.List;

public class PurchaseItemMapper {

    public static PurchaseItem toDomain(PurchaseItemJpaEntity entity) {
        return new PurchaseItem
                (
                        entity.getPurchaseItemId(),
                        entity.getPurchaseId(),
                        entity.getProductId(),
                        entity.getQuantity(),
                        entity.getUnitCost(),
                        entity.getTotalCost()
                );
    }

    public static PurchaseItemJpaEntity toEntity(PurchaseItem domain) {
        PurchaseItemJpaEntity entity = new PurchaseItemJpaEntity();

        entity.setPurchaseItemId(domain.getPurchaseItemId());
        entity.setPurchaseId(domain.getPurchaseId());
        entity.setProductId(domain.getProductId());
        entity.setQuantity(domain.getQuantity());
        entity.setUnitCost(domain.getUnitCost());
        entity.setTotalCost(domain.getTotalCost());
        return entity;
    }

    public static List<PurchaseItem> toDomainList(List<PurchaseItemJpaEntity> entities) {
        return entities.stream()
                .map(PurchaseItemMapper::toDomain)
                .toList();
    }

    public static List<PurchaseItemJpaEntity> toEntityList(List<PurchaseItem> domains) {
        return domains.stream()
                .map(PurchaseItemMapper::toEntity)
                .toList();
    }
}

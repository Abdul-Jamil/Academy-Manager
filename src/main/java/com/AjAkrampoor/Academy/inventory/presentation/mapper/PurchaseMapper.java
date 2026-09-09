package com.AjAkrampoor.Academy.inventory.presentation.mapper;

import com.AjAkrampoor.Academy.inventory.domain.model.Purchase;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.PurchaseJpaEntity;

import java.util.List;

public class PurchaseMapper {

    public static Purchase toDomain(PurchaseJpaEntity entity) {
        return new Purchase
                (
                        entity.getPurchaseId(),
                        entity.getSupplierId(),
                        entity.getBranchId(),
                        entity.getTotalAmount(),
                        entity.getDescription(),
                        entity.getPurchasedAt(),
                        entity.getCreatedBy(),
                        entity.getPurchaseStatus()
                );
    }

    public static PurchaseJpaEntity toEntity(Purchase domain) {
        PurchaseJpaEntity entity = new PurchaseJpaEntity();

        entity.setPurchaseId(domain.getPurchaseId());
        entity.setSupplierId(domain.getSupplierId());
        entity.setBranchId(domain.getBranchId());
        entity.setTotalAmount(domain.getTotalAmount());
        entity.setDescription(domain.getDescription());
        entity.setPurchasedAt(domain.getPurchasedAt());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setPurchaseStatus(domain.getPurchaseStatus());
        return entity;
    }

    public static List<Purchase> toDomainList(List<PurchaseJpaEntity> entities) {
        return entities.stream()
                .map(PurchaseMapper::toDomain)
                .toList();
    }

    public static List<PurchaseJpaEntity> toEntityList(List<Purchase> domains) {
        return domains.stream()
                .map(PurchaseMapper::toEntity)
                .toList();
    }
}

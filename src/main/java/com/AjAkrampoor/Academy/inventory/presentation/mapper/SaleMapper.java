package com.AjAkrampoor.Academy.inventory.presentation.mapper;

import com.AjAkrampoor.Academy.inventory.domain.model.Sale;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.SaleJpaEntity;

import java.util.List;

public class SaleMapper {

    public static Sale toDomain(SaleJpaEntity entity) {
        return new Sale
                (
                        entity.getSaleId(),
                        entity.getBranchId(),
                        entity.getTotalAmount(),
                        entity.getDiscountAmount(),
                        entity.getSoldBy(),
                        entity.getSoldAt(),
                        entity.getDescription(),
                        entity.getSaleStatus()
                );
    }

    public static SaleJpaEntity toEntity(Sale domain) {
        SaleJpaEntity entity = new SaleJpaEntity();

        entity.setSaleId(domain.getSaleId());
        entity.setBranchId(domain.getBranchId());
        entity.setTotalAmount(domain.getTotalAmount());
        entity.setDiscountAmount(domain.getDiscountAmount());
        entity.setSoldBy(domain.getSoldBy());
        entity.setSoldAt(domain.getSoldAt());
        entity.setDescription(domain.getDescription());
        entity.setSaleStatus(domain.getSaleStatus());
        return entity;
    }

    public static List<Sale> toDomainList(List<SaleJpaEntity> entities) {
        return entities.stream()
                .map(SaleMapper::toDomain)
                .toList();
    }

    public static List<SaleJpaEntity> toEntityList(List<Sale> domains) {
        return domains.stream()
                .map(SaleMapper::toEntity)
                .toList();
    }
}

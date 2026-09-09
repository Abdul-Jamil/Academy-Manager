package com.AjAkrampoor.Academy.inventory.presentation.mapper;

import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.SupplierJpaEntity;

import java.util.List;

public class SupplierMapper {

    public static Supplier toDomain(SupplierJpaEntity entity) {
        return new Supplier
                (
                        entity.getSupplierId(),
                        entity.getSupplierName(),
                        entity.getPhone(),
                        entity.getAddress(),
                        entity.getDescription(),
                        entity.getSupplierStatus()
                );
    }

    public static SupplierJpaEntity toEntity(Supplier domain) {
        SupplierJpaEntity entity = new SupplierJpaEntity();

        entity.setSupplierId(domain.getSupplierId());
        entity.setSupplierName(domain.getSupplierName());
        entity.setPhone(domain.getPhone());
        entity.setAddress(domain.getAddress());
        entity.setDescription(domain.getDescription());
        entity.setSupplierStatus(domain.getSupplierStatus());
        return entity;
    }

    public static List<Supplier> toDomainList(List<SupplierJpaEntity> entities) {
        return entities.stream()
                .map(SupplierMapper::toDomain)
                .toList();
    }

    public static List<SupplierJpaEntity> toEntityList(List<Supplier> domains) {
        return domains.stream()
                .map(SupplierMapper::toEntity)
                .toList();
    }
}

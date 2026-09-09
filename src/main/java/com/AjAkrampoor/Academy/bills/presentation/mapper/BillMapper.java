package com.AjAkrampoor.Academy.bills.presentation.mapper;

import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.BillJpaEntity;

import java.util.List;

public class BillMapper {

    public static Bill toDomain(BillJpaEntity entity) {
        return new Bill
                (
                        entity.getBillId(),
                        entity.getEnrollmentId(),
                        entity.getAmount(),
                        entity.getDiscount(),
                        entity.getCreatedAt(),
                        entity.getDescription(),
                        entity.getBranchId(),
                        entity.getStatus(),
                        entity.getCancelledAt(),
                        entity.getCancelledBy()
                );
    }

    public static BillJpaEntity toEntity(Bill domain) {
        BillJpaEntity entity = new BillJpaEntity();
        entity.setBillId(domain.getBillId());
        entity.setEnrollmentId(domain.getEnrollmentId());
        entity.setAmount(domain.getAmount());
        entity.setDiscount(domain.getDiscount());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setDescription(domain.getDescription());
        entity.setBranchId(domain.getBranchId());
        entity.setStatus(domain.getStatus());
        entity.setCancelledAt(domain.getCancelledAt());
        entity.setCancelledBy(domain.getCancelledBy());

        return entity;
    }

    public static List<Bill> toDomainList(List<BillJpaEntity> entities) {
        return entities.stream()
                .map(BillMapper::toDomain)
                .toList();
    }

    public static List<BillJpaEntity> toEntityList(List<Bill> domainList) {
        return domainList.stream()
                .map(BillMapper::toEntity)
                .toList();
    }
}

package com.AjAkrampoor.Academy.income.presentation.mapper;

import com.AjAkrampoor.Academy.income.domain.model.Profit;
import com.AjAkrampoor.Academy.income.infrastructure.persistence.ProfitJpaEntity;

import java.util.List;

public class ProfitMapper {

    public static Profit toDomain(ProfitJpaEntity entity) {
        return new Profit(
                entity.getProfitId(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getProfitCategoryId(),
                entity.getProfitStatus(),
                entity.getDeletedBy(),
                entity.getDeletedAt(),
                entity.getBranchId()
        );
    }

    public static ProfitJpaEntity toEntity(Profit domain) {
        ProfitJpaEntity entity = new ProfitJpaEntity();
        entity.setProfitId(domain.getProfitId());
        entity.setDescription(domain.getDescription());
        entity.setAmount(domain.getAmount());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setProfitCategoryId(domain.getProfitCategoryId());
        entity.setProfitStatus(domain.getProfitStatus());
        entity.setDeletedBy(domain.getDeletedBy());
        entity.setDeletedAt(domain.getDeletedAt());
        entity.setBranchId(domain.getBranchId());
        return entity;
    }

    public static List<Profit> toDomain(List<ProfitJpaEntity> entities) {
        return entities.stream().map(ProfitMapper::toDomain).toList();
    }

    public static List<ProfitJpaEntity> toEntity(List<Profit> domains) {
        return domains.stream().map(ProfitMapper::toEntity).toList();
    }
}

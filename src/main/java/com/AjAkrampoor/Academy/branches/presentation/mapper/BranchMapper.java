package com.AjAkrampoor.Academy.branches.presentation.mapper;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.infrastructure.persistence.BranchJpaEntity;

import java.util.List;

public class BranchMapper {

    public static Branch toDomain(BranchJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Branch(entity.getBranchId(), entity.getBranchName(), entity.getBranchStatus(), entity.isDefault());
    }

    public static BranchJpaEntity toJpaEntity(Branch domain) {
        if (domain == null) {
            return null;
        }

        BranchJpaEntity entity = new BranchJpaEntity();
        entity.setBranchId(domain.getId());
        entity.setBranchStatus(domain.getStatus());
        entity.setBranchName(domain.getName());
        entity.setDefault(domain.isDefault());
        return entity;
    }

    public static List<Branch> toDomainList(List<BranchJpaEntity> entities) {
        return entities.stream()
                .map(BranchMapper::toDomain)
                .toList();
    }

    public static List<BranchJpaEntity> ToJpaEntityList(List<Branch> domains) {
        return domains.stream()
                .map(BranchMapper::toJpaEntity)
                .toList();
    }

}
package com.AjAkrampoor.Academy.salaries.presentation.mapper;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContract;
import com.AjAkrampoor.Academy.salaries.infrastructure.persistence.SalaryContractJpaEntity;

public final class SalaryContractMapper {

    private SalaryContractMapper() {
    }

    public static SalaryContractJpaEntity toEntity(SalaryContract domain) {

        SalaryContractJpaEntity entity = new SalaryContractJpaEntity();

        entity.setSalaryContractId(domain.getId());
        entity.setStaffId(domain.getStaffId());
        entity.setType(domain.getType());
        entity.setRate(domain.getRate());
        entity.setEffectiveFrom(domain.getEffectiveFrom());
        entity.setEffectiveTo(domain.getEffectiveTo());

        return entity;
    }

    public static SalaryContract toDomain(SalaryContractJpaEntity entity) {

        return new SalaryContract(
                entity.getSalaryContractId(),
                entity.getStaffId(),
                entity.getType(),
                entity.getRate(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo()
        );
    }
}
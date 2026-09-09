package com.AjAkrampoor.Academy.salaries.presentation.mapper;

import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalary;
import com.AjAkrampoor.Academy.salaries.infrastructure.persistence.MonthlySalaryJpaEntity;

public final class MonthlySalaryMapper {

    public static MonthlySalary toDomain(MonthlySalaryJpaEntity entity) {

        return new MonthlySalary(
                entity.getMonthlySalaryId(),
                entity.getStaffId(),
                entity.getSalaryMonth(),
                entity.getEarnedAmount(),
                entity.getPaidAmount(),
                entity.getFinalizedAt()
        );
    }

    public static MonthlySalaryJpaEntity toEntity(MonthlySalary domain) {

        MonthlySalaryJpaEntity entity = new MonthlySalaryJpaEntity();

        entity.setMonthlySalaryId(domain.getId());
        entity.setStaffId(domain.getStaffId());
        entity.setSalaryMonth(domain.getSalaryMonth());
        entity.setEarnedAmount(domain.getEarnedAmount());
        entity.setPaidAmount(domain.getPaidAmount());
        entity.setFinalizedAt(domain.getFinalizedAt());

        return entity;
    }
}
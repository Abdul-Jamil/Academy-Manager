package com.AjAkrampoor.Academy.salaries.presentation.mapper;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPayment;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentStatus;
import com.AjAkrampoor.Academy.salaries.infrastructure.persistence.SalaryPaymentJpaEntity;

public final class SalaryPaymentMapper {

    private SalaryPaymentMapper() {
    }

    public static SalaryPaymentJpaEntity toEntity(SalaryPayment domain) {

        SalaryPaymentJpaEntity entity = new SalaryPaymentJpaEntity();

        entity.setSalaryPaymentId(domain.getId());
        entity.setStaffId(domain.getStaffId());
        entity.setSalaryMonth(domain.getSalaryMonth());
        entity.setAmount(domain.getAmount());
        entity.setPaymentDate(domain.getPaymentDate());
        entity.setStatus(domain.getStatus());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setCancelledAt(domain.getCancelledAt());

        return entity;
    }

    public static SalaryPayment toDomain(SalaryPaymentJpaEntity entity) {

        SalaryPayment domain = new SalaryPayment(
                entity.getSalaryPaymentId(),
                entity.getStaffId(),
                entity.getSalaryMonth(),
                entity.getAmount(),
                entity.getPaymentDate(),
                entity.getStatus(),
                entity.getCreatedBy()
        );

        if (entity.getStatus() == SalaryPaymentStatus.CANCELLED) {
            /*
             * The domain object intentionally owns the cancellation transition.
             * Calling cancel() reconstructs the cancelled state when reading
             * an entity back from persistence.
             */
            domain.cancel();
        }

        return domain;
    }
}
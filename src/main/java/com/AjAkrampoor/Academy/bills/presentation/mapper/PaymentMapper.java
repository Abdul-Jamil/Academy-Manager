package com.AjAkrampoor.Academy.bills.presentation.mapper;

import com.AjAkrampoor.Academy.bills.domain.model.Payment;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.PaymentJpaEntity;

import java.util.List;

public class PaymentMapper {

    public static Payment toDomain(PaymentJpaEntity entity) {
        return new Payment
                (
                        entity.getPaymentId(),
                        entity.getBillId(),
                        entity.getReceivedBy(),
                        entity.getAmount(),
                        entity.getDescription(),
                        entity.getDate(),
                        entity.getCancelledAt(),
                        entity.getCancelledBy(),
                        entity.getStatus()
                );
    }

    public static PaymentJpaEntity toEntity(Payment domain) {
        PaymentJpaEntity entity = new PaymentJpaEntity();
        entity.setPaymentId(domain.getPaymentId());
        entity.setBillId(domain.getBillId());
        entity.setReceivedBy(domain.getReceivedBy());
        entity.setAmount(domain.getAmount());
        entity.setDescription(domain.getDescription());
        entity.setDate(domain.getDate());
        entity.setCancelledAt(domain.getCancelledAt());
        entity.setCancelledBy(domain.getCancelledBy());
        entity.setStatus(domain.getStatus());

        return entity;
    }

    public static List<Payment> toDomainList(List<PaymentJpaEntity> entities) {
        return entities.stream()
                .map(PaymentMapper::toDomain)
                .toList();
    }

    public static List<PaymentJpaEntity> toEntityList(List<Payment> domains) {
        return domains.stream()
                .map(PaymentMapper::toEntity)
                .toList();
    }
}

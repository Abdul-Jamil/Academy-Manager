package com.AjAkrampoor.Academy.bills.infrastructure.repository;

import com.AjAkrampoor.Academy.bills.application.dto.PaymentFilter;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.model.Payment;
import com.AjAkrampoor.Academy.bills.domain.model.PaymentId;
import com.AjAkrampoor.Academy.bills.domain.repository.PaymentRepository;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.PaymentJpaEntity;
import com.AjAkrampoor.Academy.bills.presentation.mapper.PaymentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository repository;

    public PaymentRepositoryImpl(PaymentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Payment> findById(PaymentId id) {
        return repository.findById(id).map(PaymentMapper::toDomain);
    }

    @Override
    public boolean existsById(PaymentId id) {
        return repository.existsById(id);
    }

    @Override
    public Page<Payment> findAll(Pageable pageable, PaymentFilter paymentFilter) {
        List<Specification<PaymentJpaEntity>> specifications = PaymentSpecifications.specifications(paymentFilter);

        Specification<PaymentJpaEntity> specs = specifications.stream()
                .reduce(Specification::and)
                .orElse(null);

        return repository.findAll(specs, pageable).map(PaymentMapper::toDomain);
    }

    @Override
    public Payment save(Payment payment) {
        PaymentJpaEntity save = repository.save(PaymentMapper.toEntity(payment));
        return PaymentMapper.toDomain(save);
    }

    @Override
    public Map<BillId, BigDecimal> getPaidAmountsByBillIds(Set<BillId> billIds) {
        if (billIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<Object[]> results = repository.sumPaidAmountByBillIds(billIds);

        return results.stream()
                .filter(row -> row[0] != null)
                .collect(Collectors.toMap(
                        row -> (BillId) row[0],
                        row -> (BigDecimal) row[1]
                ));
    }
}

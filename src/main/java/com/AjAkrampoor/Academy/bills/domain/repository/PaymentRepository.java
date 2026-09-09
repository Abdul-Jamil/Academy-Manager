package com.AjAkrampoor.Academy.bills.domain.repository;

import com.AjAkrampoor.Academy.bills.application.dto.PaymentFilter;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.model.Payment;
import com.AjAkrampoor.Academy.bills.domain.model.PaymentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface PaymentRepository {

    Optional<Payment> findById(PaymentId id);

    boolean existsById(PaymentId id);

    Page<Payment> findAll(Pageable pageable, PaymentFilter paymentFilter);

    Payment save(Payment payment);

    Map<BillId, BigDecimal> getPaidAmountsByBillIds(Set<BillId> billIds);
}

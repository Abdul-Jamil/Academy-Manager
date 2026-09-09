package com.AjAkrampoor.Academy.bills.domain.repository;

import com.AjAkrampoor.Academy.bills.application.dto.BillFilter;
import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BillRepository {

    Optional<Bill> findById(BillId id);

    Bill save(Bill bill);

    boolean existsById(BillId id);

    Page<Bill> findAll(Pageable pageable, BillFilter billFilter);

    List<Bill> findAllById(Set<BillId> billIds);

    List<Bill> findByEnrollmentId(EnrollmentId enrollmentId);
}

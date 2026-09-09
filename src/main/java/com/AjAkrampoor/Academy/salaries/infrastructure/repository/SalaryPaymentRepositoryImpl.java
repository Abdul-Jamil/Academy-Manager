package com.AjAkrampoor.Academy.salaries.infrastructure.repository;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPayment;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentId;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryPaymentRepository;
import com.AjAkrampoor.Academy.salaries.infrastructure.persistence.SalaryPaymentJpaEntity;
import com.AjAkrampoor.Academy.salaries.presentation.mapper.SalaryPaymentMapper;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SalaryPaymentRepositoryImpl
        implements SalaryPaymentRepository {

    private final SalaryPaymentJpaRepository repository;

    public SalaryPaymentRepositoryImpl(
            SalaryPaymentJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public SalaryPayment save(SalaryPayment salaryPayment) {
        SalaryPaymentJpaEntity saved =
                repository.save(
                        SalaryPaymentMapper.toEntity(salaryPayment)
                );

        return SalaryPaymentMapper.toDomain(saved);
    }

    @Override
    public Optional<SalaryPayment> findById(
            SalaryPaymentId salaryPaymentId
    ) {
        return repository.findById(salaryPaymentId)
                .map(SalaryPaymentMapper::toDomain);
    }

    @Override
    public List<SalaryPayment> findByStaffAndMonth(
            StaffId staffId,
            SalaryMonth salaryMonth
    ) {
        return repository.findByStaffAndMonth(
                        staffId,
                        salaryMonth.getYear(),
                        salaryMonth.getMonth()
                )
                .stream()
                .map(SalaryPaymentMapper::toDomain)
                .toList();
    }

    @Override
    public List<SalaryPayment> findByMonth(
            SalaryMonth salaryMonth
    ) {
        return repository.findByMonth(
                        salaryMonth.getYear(),
                        salaryMonth.getMonth()
                )
                .stream()
                .map(SalaryPaymentMapper::toDomain)
                .toList();
    }

    @Override
    public List<SalaryPayment> findByStaff(StaffId staffId) {
        return repository.findByStaff(staffId)
                .stream()
                .map(SalaryPaymentMapper::toDomain)
                .toList();
    }

    @Override
    public List<SalaryPayment> findAll() {
        return repository.findAllPayments()
                .stream()
                .map(SalaryPaymentMapper::toDomain)
                .toList();
    }
}
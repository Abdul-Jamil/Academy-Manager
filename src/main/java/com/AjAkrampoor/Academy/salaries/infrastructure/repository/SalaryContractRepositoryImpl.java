package com.AjAkrampoor.Academy.salaries.infrastructure.repository;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContract;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractId;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryContractRepository;
import com.AjAkrampoor.Academy.salaries.infrastructure.persistence.SalaryContractJpaEntity;
import com.AjAkrampoor.Academy.salaries.presentation.mapper.SalaryContractMapper;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class SalaryContractRepositoryImpl
        implements SalaryContractRepository {

    private final SalaryContractJpaRepository repository;

    public SalaryContractRepositoryImpl(
            SalaryContractJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public SalaryContract save(SalaryContract salaryContract) {
        SalaryContractJpaEntity saved =
                repository.save(
                        SalaryContractMapper.toEntity(salaryContract)
                );

        return SalaryContractMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(SalaryContractId salaryContractId) {
        return repository.existsById(salaryContractId);
    }

    @Override
    public Optional<SalaryContract> findById(
            SalaryContractId salaryContractId
    ) {
        return repository.findById(salaryContractId)
                .map(SalaryContractMapper::toDomain);
    }

    @Override
    public Optional<SalaryContract> findActiveByStaffId(
            StaffId staffId,
            LocalDate date
    ) {
        return repository.findActiveByStaffId(staffId, date)
                .map(SalaryContractMapper::toDomain);
    }

    @Override
    public List<SalaryContract> findByStaffId(StaffId staffId) {
        return repository.findByStaffId(staffId)
                .stream()
                .map(SalaryContractMapper::toDomain)
                .toList();
    }

    @Override
    public List<SalaryContract> findActiveOn(LocalDate date) {
        return repository.findActiveOn(date)
                .stream()
                .map(SalaryContractMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsOverlapping(
            StaffId staffId,
            LocalDate effectiveFrom,
            LocalDate effectiveTo
    ) {
        return repository.existsOverlapping(
                staffId,
                effectiveFrom,
                effectiveTo
        );
    }
}
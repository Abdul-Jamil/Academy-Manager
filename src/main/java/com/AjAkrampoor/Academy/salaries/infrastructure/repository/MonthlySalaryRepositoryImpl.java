package com.AjAkrampoor.Academy.salaries.infrastructure.repository;

import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalary;
import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalaryId;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.repository.MonthlySalaryRepository;
import com.AjAkrampoor.Academy.salaries.infrastructure.persistence.MonthlySalaryJpaEntity;
import com.AjAkrampoor.Academy.salaries.presentation.mapper.MonthlySalaryMapper;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MonthlySalaryRepositoryImpl
        implements MonthlySalaryRepository {

    private final MonthlySalaryJpaRepository repository;

    public MonthlySalaryRepositoryImpl(
            MonthlySalaryJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public MonthlySalary save(MonthlySalary monthlySalary) {
        MonthlySalaryJpaEntity saved =
                repository.save(
                        MonthlySalaryMapper.toEntity(monthlySalary)
                );

        return MonthlySalaryMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(MonthlySalaryId monthlySalaryId) {
        return repository.existsById(monthlySalaryId);
    }

    @Override
    public Optional<MonthlySalary> findById(
            MonthlySalaryId monthlySalaryId
    ) {
        return repository.findById(monthlySalaryId)
                .map(MonthlySalaryMapper::toDomain);
    }

    @Override
    public Optional<MonthlySalary> findByStaffAndMonth(
            StaffId staffId,
            SalaryMonth salaryMonth
    ) {
        return repository.findByStaffAndMonth(
                        staffId,
                        salaryMonth.getYear(),
                        salaryMonth.getMonth()
                )
                .map(MonthlySalaryMapper::toDomain);
    }

    @Override
    public List<MonthlySalary> findByMonth(
            SalaryMonth salaryMonth
    ) {
        return repository.findByMonth(
                        salaryMonth.getYear(),
                        salaryMonth.getMonth()
                )
                .stream()
                .map(MonthlySalaryMapper::toDomain)
                .toList();
    }

    @Override
    public List<MonthlySalary> findByStaff(
            StaffId staffId
    ) {
        return repository.findByStaff(staffId)
                .stream()
                .map(MonthlySalaryMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByStaffAndMonth(
            StaffId staffId,
            SalaryMonth salaryMonth
    ) {
        return repository.existsByStaffAndMonth(
                staffId,
                salaryMonth.getYear(),
                salaryMonth.getMonth()
        );
    }
}
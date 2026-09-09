package com.AjAkrampoor.Academy.salaries.domain.repository;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContract;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalaryContractRepository {

    SalaryContract save(SalaryContract salaryContract);

    boolean existsById(SalaryContractId salaryContractId);

    Optional<SalaryContract> findById(SalaryContractId salaryContractId);

    Optional<SalaryContract> findActiveByStaffId(
            StaffId staffId,
            LocalDate date
    );

    List<SalaryContract> findByStaffId(StaffId staffId);

    List<SalaryContract> findActiveOn(LocalDate date);

    boolean existsOverlapping(
            StaffId staffId,
            LocalDate effectiveFrom,
            LocalDate effectiveTo
    );
}
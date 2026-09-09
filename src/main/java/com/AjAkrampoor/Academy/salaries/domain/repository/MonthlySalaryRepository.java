package com.AjAkrampoor.Academy.salaries.domain.repository;


import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalary;
import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalaryId;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.util.List;
import java.util.Optional;

public interface MonthlySalaryRepository {

    MonthlySalary save(MonthlySalary monthlySalary);

    boolean existsById(MonthlySalaryId monthlySalaryId);

    Optional<MonthlySalary> findById(MonthlySalaryId monthlySalaryId);

    Optional<MonthlySalary> findByStaffAndMonth(
            StaffId staffId,
            SalaryMonth salaryMonth
    );

    List<MonthlySalary> findByMonth(SalaryMonth salaryMonth);

    List<MonthlySalary> findByStaff(StaffId staffId);

    boolean existsByStaffAndMonth(
            StaffId staffId,
            SalaryMonth salaryMonth
    );
}
package com.AjAkrampoor.Academy.salaries.domain.repository;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPayment;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.util.List;
import java.util.Optional;

public interface SalaryPaymentRepository {

    SalaryPayment save(SalaryPayment salaryPayment);

    Optional<SalaryPayment> findById(SalaryPaymentId salaryPaymentId);

    List<SalaryPayment> findByStaffAndMonth(
            StaffId staffId,
            SalaryMonth salaryMonth
    );

    List<SalaryPayment> findByMonth(SalaryMonth salaryMonth);

    List<SalaryPayment> findByStaff(StaffId staffId);

    List<SalaryPayment> findAll();
}
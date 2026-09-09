package com.AjAkrampoor.Academy.salaries.application.usecases.monthly;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.application.SalaryCalculationService;
import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalary;
import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalaryId;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.repository.MonthlySalaryRepository;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class FinalizeMonthlySalaryUseCase {

    private final MonthlySalaryRepository monthlySalaryRepository;
    private final SalaryCalculationService calculationService;
    private final SalaryAuthorizationService authorizationService;

    public FinalizeMonthlySalaryUseCase(
            MonthlySalaryRepository monthlySalaryRepository,
            SalaryCalculationService calculationService,
            SalaryAuthorizationService authorizationService
    ) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.calculationService = calculationService;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public MonthlySalary execute(
            String userId,
            String staffId,
            int year,
            int month) {
        Staff staff = authorizationService.requireAccessibleStaff(userId, StaffId.from(staffId));

        SalaryMonth salaryMonth = SalaryMonth.of(year, month);

        if (!salaryMonth.isBefore(SalaryMonth.current())) {
            throw new IllegalArgumentException("Monthly salary can only be finalized after the month has ended");
        }

        if (monthlySalaryRepository.existsByStaffAndMonth(staff.getId(), salaryMonth)) {
            throw new IllegalStateException("Monthly salary has already been finalized");
        }

        Money earned = calculationService.calculateEarned(
                staff,
                salaryMonth,
                salaryMonth.getEndDate()
        );

        Money paid = calculationService.calculatePaid(staff.getId(), salaryMonth);

        MonthlySalary monthlySalary =
                new MonthlySalary(
                        MonthlySalaryId.newId(),
                        staff.getId(),
                        salaryMonth,
                        earned,
                        paid,
                        LocalDate.now()
                );

        return monthlySalaryRepository.save(monthlySalary);
    }
}
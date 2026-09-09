package com.AjAkrampoor.Academy.salaries.application.usecases.monthly;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalary;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.repository.MonthlySalaryRepository;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetMonthlySalaryUseCase {

    private final MonthlySalaryRepository monthlySalaryRepository;
    private final SalaryAuthorizationService authorizationService;

    public GetMonthlySalaryUseCase(
            MonthlySalaryRepository monthlySalaryRepository,
            SalaryAuthorizationService authorizationService
    ) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional(readOnly = true)
    public MonthlySalary execute(
            String userId,
            String staffId,
            int year,
            int month
    ) {
        StaffId id = StaffId.from(staffId);

        authorizationService.requireAccessibleStaff(
                userId,
                id
        );

        return monthlySalaryRepository
                .findByStaffAndMonth(
                        id,
                        SalaryMonth.of(
                                year,
                                month
                        )
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Monthly salary not found"
                        )
                );
    }
}
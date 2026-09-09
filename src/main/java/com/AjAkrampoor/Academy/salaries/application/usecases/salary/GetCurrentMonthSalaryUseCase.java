package com.AjAkrampoor.Academy.salaries.application.usecases.salary;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.application.SalaryCalculation;
import com.AjAkrampoor.Academy.salaries.application.SalaryCalculationService;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetCurrentMonthSalaryUseCase {

    private final SalaryCalculationService calculationService;
    private final SalaryAuthorizationService authorizationService;

    public GetCurrentMonthSalaryUseCase(
            SalaryCalculationService calculationService,
            SalaryAuthorizationService authorizationService
    ) {
        this.calculationService = calculationService;
        this.authorizationService = authorizationService;
    }

    @Transactional(readOnly = true)
    public SalaryCalculation execute(
            String userId,
            String staffId
    ) {
        return calculationService.calculate(
                authorizationService.requireAccessibleStaff(
                        userId,
                        StaffId.from(staffId)
                ),
                SalaryMonth.current()
        );
    }
}
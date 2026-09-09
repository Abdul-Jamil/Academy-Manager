package com.AjAkrampoor.Academy.salaries.application.usecases.salary;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.application.SalaryCalculationService;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetAvailableSalaryToPayUseCase {

    private final SalaryCalculationService calculationService;
    private final SalaryAuthorizationService authorizationService;

    public GetAvailableSalaryToPayUseCase(
            SalaryCalculationService calculationService,
            SalaryAuthorizationService authorizationService
    ) {
        this.calculationService = calculationService;
        this.authorizationService = authorizationService;
    }

    @Transactional(readOnly = true)
    public Money execute(
            String userId,
            String staffId,
            int year,
            int month
    ) {
        Staff staff =
                authorizationService.requireAccessibleStaff(
                        userId,
                        StaffId.from(staffId)
                );

        Money earned =
                calculationService.calculateEarned(
                        staff,
                        SalaryMonth.of(year, month),
                        java.time.LocalDate.now()
                );

        Money paid =
                calculationService.calculatePaid(
                        staff.getId(),
                        SalaryMonth.of(year, month)
                );

        return earned.subtract(paid);
    }
}
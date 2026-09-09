package com.AjAkrampoor.Academy.salaries.application.usecases.monthly;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalary;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.repository.MonthlySalaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetMonthlySalariesUseCase {

    private final MonthlySalaryRepository monthlySalaryRepository;
    private final SalaryAuthorizationService authorizationService;

    public GetMonthlySalariesUseCase(
            MonthlySalaryRepository monthlySalaryRepository,
            SalaryAuthorizationService authorizationService
    ) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional(readOnly = true)
    public List<MonthlySalary> execute(
            String userId,
            int year,
            int month
    ) {
        SalaryMonth salaryMonth =
                SalaryMonth.of(year, month);

        return monthlySalaryRepository
                .findByMonth(salaryMonth)
                .stream()
                .filter(monthlySalary -> {
                    authorizationService.requireAccessibleStaff(
                            userId,
                            monthlySalary.getStaffId()
                    );
                    return true;
                })
                .toList();
    }
}
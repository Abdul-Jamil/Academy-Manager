package com.AjAkrampoor.Academy.salaries.application.usecases.salary;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.application.SalaryCalculation;
import com.AjAkrampoor.Academy.salaries.application.SalaryCalculationService;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryContractRepository;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetCurrentMonthSalariesUseCase {

    private final SalaryContractRepository salaryContractRepository;
    private final StaffRepository staffRepository;
    private final SalaryAuthorizationService authorizationService;
    private final SalaryCalculationService calculationService;

    public GetCurrentMonthSalariesUseCase(
            SalaryContractRepository salaryContractRepository,
            StaffRepository staffRepository,
            SalaryAuthorizationService authorizationService,
            SalaryCalculationService calculationService
    ) {
        this.salaryContractRepository = salaryContractRepository;
        this.staffRepository = staffRepository;
        this.authorizationService = authorizationService;
        this.calculationService = calculationService;
    }

    @Transactional(readOnly = true)
    public List<SalaryCalculation> execute(
            String userId
    ) {
        LocalDate today = LocalDate.now();

        return salaryContractRepository.findActiveOn(today)
                .stream()
                .map(contract ->
                        authorizationService.requireAccessibleStaff(
                                userId,
                                contract.getStaffId()
                        )
                )
                .distinct()
                .map(staff ->
                        calculationService.calculate(
                                staff,
                                SalaryMonth.current()
                        )
                )
                .toList();
    }
}
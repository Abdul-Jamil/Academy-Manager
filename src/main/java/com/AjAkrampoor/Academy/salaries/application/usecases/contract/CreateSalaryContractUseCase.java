package com.AjAkrampoor.Academy.salaries.application.usecases.contract;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContract;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractId;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractType;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryContractRepository;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class CreateSalaryContractUseCase {

    private final SalaryContractRepository salaryContractRepository;
    private final SalaryAuthorizationService authorizationService;

    public CreateSalaryContractUseCase(
            SalaryContractRepository salaryContractRepository,
            SalaryAuthorizationService authorizationService
    ) {
        this.salaryContractRepository = salaryContractRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public SalaryContract execute(
            String userId,
            String staffId,
            SalaryContractType type,
            BigDecimal rate,
            LocalDate effectiveFrom
    ) {
        StaffId id = StaffId.from(staffId);

        authorizationService.requireAccessibleStaff(
                userId,
                id
        );

        if (type == null) {
            throw new IllegalArgumentException(
                    "Salary contract type cannot be null"
            );
        }

        if (rate == null) {
            throw new IllegalArgumentException(
                    "Salary rate cannot be null"
            );
        }

        if (effectiveFrom == null) {
            throw new IllegalArgumentException(
                    "Effective-from date cannot be null"
            );
        }

        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Salary rate must be greater than zero"
            );
        }

        if (salaryContractRepository.existsOverlapping(
                id,
                effectiveFrom,
                null
        )) {
            throw new IllegalStateException(
                    "Staff member already has a salary contract covering this date"
            );
        }

        SalaryContract contract =
                new SalaryContract(
                        SalaryContractId.newId(),
                        id,
                        type,
                        new Money(rate),
                        effectiveFrom
                );

        return salaryContractRepository.save(contract);
    }
}
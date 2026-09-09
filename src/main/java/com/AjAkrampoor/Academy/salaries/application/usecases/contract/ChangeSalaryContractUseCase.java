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
public class ChangeSalaryContractUseCase {

    private final SalaryContractRepository salaryContractRepository;
    private final SalaryAuthorizationService authorizationService;

    public ChangeSalaryContractUseCase(
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
            SalaryContractType newType,
            BigDecimal newRate,
            LocalDate effectiveFrom
    ) {
        StaffId id = StaffId.from(staffId);

        authorizationService.requireAccessibleStaff(
                userId,
                id
        );

        if (newType == null) {
            throw new IllegalArgumentException(
                    "Salary contract type cannot be null"
            );
        }

        if (newRate == null ||
                newRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Salary rate must be greater than zero"
            );
        }

        SalaryContract current =
                salaryContractRepository.findActiveByStaffId(
                        id,
                        effectiveFrom
                ).orElseThrow(() ->
                        new IllegalStateException(
                                "No salary contract is active for the requested change date"
                        )
                );

        if (!effectiveFrom.isAfter(
                current.getEffectiveFrom()
        )) {
            throw new IllegalArgumentException(
                    "New salary contract must start after the current contract starts"
            );
        }

        LocalDate oldEnd =
                effectiveFrom.minusDays(1);

        current.endOn(oldEnd);

        salaryContractRepository.save(current);

        SalaryContract replacement =
                new SalaryContract(
                        SalaryContractId.newId(),
                        id,
                        newType,
                        new Money(newRate),
                        effectiveFrom
                );

        return salaryContractRepository.save(
                replacement
        );
    }
}
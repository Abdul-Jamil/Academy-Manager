package com.AjAkrampoor.Academy.salaries.application.usecases.contract;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContract;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryContractRepository;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetActiveSalaryContractsUseCase {

    private final SalaryContractRepository salaryContractRepository;
    private final SalaryAuthorizationService authorizationService;

    public GetActiveSalaryContractsUseCase(
            SalaryContractRepository salaryContractRepository,
            SalaryAuthorizationService authorizationService
    ) {
        this.salaryContractRepository = salaryContractRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional(readOnly = true)
    public List<SalaryContract> execute(
            String userId,
            String staffId
    ) {
        LocalDate today = LocalDate.now();

        if (staffId != null && !staffId.isBlank()) {

            StaffId targetStaffId =
                    StaffId.from(staffId);

            authorizationService.requireAccessibleStaff(
                    userId,
                    targetStaffId
            );

            return salaryContractRepository
                    .findActiveByStaffId(
                            targetStaffId,
                            today
                    )
                    .map(List::of)
                    .orElseGet(List::of);
        }

        return salaryContractRepository
                .findActiveOn(today)
                .stream()
                .filter(contract -> {
                    authorizationService.requireAccessibleStaff(
                            userId,
                            contract.getStaffId()
                    );
                    return true;
                })
                .toList();
    }
}
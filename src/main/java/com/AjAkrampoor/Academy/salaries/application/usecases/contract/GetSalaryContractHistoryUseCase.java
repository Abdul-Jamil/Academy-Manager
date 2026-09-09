package com.AjAkrampoor.Academy.salaries.application.usecases.contract;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContract;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryContractRepository;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetSalaryContractHistoryUseCase {

    private final SalaryContractRepository salaryContractRepository;
    private final SalaryAuthorizationService authorizationService;

    public GetSalaryContractHistoryUseCase(
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
        StaffId id = StaffId.from(staffId);

        authorizationService.requireAccessibleStaff(
                userId,
                id
        );

        return salaryContractRepository.findByStaffId(id);
    }
}
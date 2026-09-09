package com.AjAkrampoor.Academy.salaries.application.usecases.monthly;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalary;
import com.AjAkrampoor.Academy.salaries.domain.repository.MonthlySalaryRepository;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetStaffSalaryHistoryUseCase {

    private final MonthlySalaryRepository monthlySalaryRepository;
    private final SalaryAuthorizationService authorizationService;

    public GetStaffSalaryHistoryUseCase(
            MonthlySalaryRepository monthlySalaryRepository,
            SalaryAuthorizationService authorizationService
    ) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional(readOnly = true)
    public List<MonthlySalary> execute(String userId, String staffId) {
        StaffId id = StaffId.from(staffId);

        authorizationService.requireAccessibleStaff(
                userId,
                id
        );

        return monthlySalaryRepository.findByStaff(id);
    }
}
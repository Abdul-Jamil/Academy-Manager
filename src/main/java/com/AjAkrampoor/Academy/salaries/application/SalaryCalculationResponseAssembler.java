package com.AjAkrampoor.Academy.salaries.application;

import com.AjAkrampoor.Academy.salaries.application.dto.SalaryCalculationResponse;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.stereotype.Component;

@Component
public class SalaryCalculationResponseAssembler {
    private final StaffRepository staffRepository;

    public SalaryCalculationResponseAssembler(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    public SalaryCalculationResponse toResponse(SalaryCalculation domain) {
        Staff staff = staffRepository.findById(domain.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        return new SalaryCalculationResponse(
                staff.getName().getFullName(),
                domain.getSalaryMonth().toString(),
                domain.getEarnedAmount().getAmount(),
                domain.getPaidAmount().getAmount(),
                domain.getAvailableAmount().getAmount(),
                domain.getProjectedAmount().getAmount()
        );
    }
}
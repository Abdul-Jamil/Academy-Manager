package com.AjAkrampoor.Academy.salaries.application;

import com.AjAkrampoor.Academy.salaries.application.dto.SalaryContractResponse;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContract;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.stereotype.Component;

@Component
public class SalaryContractResponseAssembler {
    private final StaffRepository staffRepository;

    public SalaryContractResponseAssembler(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public SalaryContractResponse toResponse(SalaryContract domain) {
        Staff staff = staffRepository.findById(domain.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        return new SalaryContractResponse(
                domain.getId().toString(),
                staff.getName().getFullName(),
                domain.getType(),
                domain.getRate().getAmount(),
                domain.getEffectiveFrom(),
                domain.getEffectiveTo()
        );
    }
}
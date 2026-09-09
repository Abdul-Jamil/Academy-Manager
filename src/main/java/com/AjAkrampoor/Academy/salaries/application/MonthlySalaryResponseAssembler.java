package com.AjAkrampoor.Academy.salaries.application;

import com.AjAkrampoor.Academy.salaries.application.dto.MonthlySalaryResponse;
import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalary;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.stereotype.Component;

@Component
public class MonthlySalaryResponseAssembler {
    private final StaffRepository staffRepository;

    public MonthlySalaryResponseAssembler(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    public MonthlySalaryResponse toResponse(MonthlySalary domain) {
        Staff staff = staffRepository.findById(domain.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        return new MonthlySalaryResponse(
                domain.getId().toString(),
                staff.getName().getFullName(),
                domain.getSalaryMonth().toString(),
                domain.getEarnedAmount().getAmount(),
                domain.getPaidAmount().getAmount(),
                domain.getRemainingAmount().getAmount(),
                domain.getFinalizedAt()
        );
    }
}
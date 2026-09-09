package com.AjAkrampoor.Academy.salaries.application;

import com.AjAkrampoor.Academy.salaries.application.dto.SalaryPaymentResponse;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPayment;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.stereotype.Component;

@Component
public class SalaryPaymentResponseAssembler {
    private final StaffRepository staffRepository;

    public SalaryPaymentResponseAssembler(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public SalaryPaymentResponse toResponse(SalaryPayment domain) {
        Staff staff = staffRepository.findById(domain.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        return new SalaryPaymentResponse(
                domain.getId().toString(),
                staff.getName().getFullName(),
                domain.getSalaryMonth().toString(),
                domain.getAmount().getAmount(),
                domain.getPaymentDate(),
                domain.getStatus(),
                domain.getCreatedBy().toString(),
                domain.getCancelledAt()
        );
    }
}
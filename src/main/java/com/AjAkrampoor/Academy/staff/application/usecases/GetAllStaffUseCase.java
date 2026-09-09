package com.AjAkrampoor.Academy.staff.application.usecases;

import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllStaffUseCase {
    private final StaffRepository staffRepository;

    public GetAllStaffUseCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> execute() {
        return staffRepository.findAll();
    }
}

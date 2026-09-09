package com.AjAkrampoor.Academy.staff.application.usecases;

import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StaffActivateUseCase {
    private final StaffRepository repository;

    public StaffActivateUseCase(StaffRepository staffRepository) {
        this.repository = staffRepository;
    }

    @Transactional
    public Staff execute(String staffId) {
        Staff staff = repository.findById(StaffId.from(staffId)).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        if (staff.isActive()) {
            return staff;
        }
        staff.activate();
        return repository.save(staff);
    }
}

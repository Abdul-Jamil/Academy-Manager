package com.AjAkrampoor.Academy.staff.application.usecases;

import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StaffUpdateUseCase {
    private final StaffRepository repository;

    public StaffUpdateUseCase(StaffRepository staffRepository) {
        this.repository = staffRepository;
    }

    @Transactional
    public Staff execute(String staffId, String firstName, String lastName, String phoneNumberStr, String descriptionStr) {
        Staff staff = repository.findById(StaffId.from(staffId)).orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        staff.updateName(firstName, lastName);

        if (phoneNumberStr != null) {
            staff.updatePhoneNumber(new PhoneNumber(phoneNumberStr));
        }

        if (descriptionStr != null) {
            staff.updateDescription(new Description(descriptionStr));
        }

        return repository.save(staff);
    }
}

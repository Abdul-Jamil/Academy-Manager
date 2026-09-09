package com.AjAkrampoor.Academy.staff.application.usecases;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Name;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffStatus;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class StaffCreateUseCase {
    private final StaffRepository staffRepository;
    private final BranchRepository branchRepository;
    private final int MAX_RETRIES = 10;

    public StaffCreateUseCase(StaffRepository repository, BranchRepository branchRepository) {
        this.staffRepository = repository;
        this.branchRepository = branchRepository;
    }

    @Transactional
    public Staff execute(String firstName, String lastName, String phoneNumberStr, String descriptionStr, UUID branchUUID) {
        if (!branchRepository.existsById(new BranchId(branchUUID))) {
            throw new IllegalArgumentException("No such branch found");
        }

        Name name = new Name(firstName, lastName);
        PhoneNumber phoneNumber = new PhoneNumber(phoneNumberStr);
        Description description = new Description(descriptionStr);
        BranchId branchId = new BranchId(branchUUID);

        StaffId staffId;
        for (int i = 0; i < MAX_RETRIES; i++) {
            staffId = StaffId.generate();
            if (!staffRepository.existsById(staffId)) {
                Staff newStaff = new Staff(staffId, name, phoneNumber, description, StaffStatus.ACTIVE, null, branchId);
                return staffRepository.save(newStaff);
            }
        }
        throw new IllegalArgumentException("Could not create staff after several retries, please try again later");
    }
}

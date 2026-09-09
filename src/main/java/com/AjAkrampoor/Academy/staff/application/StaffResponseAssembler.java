package com.AjAkrampoor.Academy.staff.application;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.staff.application.dto.StaffResponse;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StaffResponseAssembler {
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    public StaffResponseAssembler(UserRepository userRepository, BranchRepository branchRepository) {
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
    }


    public StaffResponse toResponse(Staff staff) {
        Branch branch = branchRepository.findById(staff.getBranchId()).orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        Optional<User> byStaffId = userRepository.findByStaffId(staff.getStaffId());
        boolean hasStaff = false;
        if (byStaffId.isPresent()) {
            hasStaff = true;
        }
        return new StaffResponse(
                staff.getStaffId().getId(),
                staff.getName().getFirstName(),
                staff.getName().getLastName(),
                staff.getPhoneNumber().getNumber(),
                staff.getDescription().getValue(),
                staff.getStatus(),
                staff.getTerminationDate(),
                branch.getName().toString(),
                hasStaff
        );
    }
}

package com.AjAkrampoor.Academy.staff.application.usecases;

import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllBranchStaffUseCase {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;

    public GetAllBranchStaffUseCase(StaffRepository branchRepository, UserRepository userRepository) {
        this.staffRepository = branchRepository;
        this.userRepository = userRepository;
    }

    public List<Staff> execute(UserId userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        return staffRepository.findAllByBranchId(staff.getBranchId());
    }
}

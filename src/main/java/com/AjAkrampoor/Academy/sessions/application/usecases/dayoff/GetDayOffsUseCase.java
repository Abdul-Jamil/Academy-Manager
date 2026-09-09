package com.AjAkrampoor.Academy.sessions.application.usecases.dayoff;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOff;
import com.AjAkrampoor.Academy.sessions.domain.repository.DayOffRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetDayOffsUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final DayOffRepository dayOffRepository;

    public GetDayOffsUseCase(UserRepository userRepository,
                             RoleRepository roleRepository,
                             StaffRepository staffRepository,
                             DayOffRepository dayOffRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.dayOffRepository = dayOffRepository;
    }

    @Transactional(readOnly = true)
    public Page<DayOff> execute(String userId, Pageable pageable) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Role role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff record not found"));

        if (role.isSuperAdmin()) {
            return dayOffRepository.findAll(pageable);
        } else {
            return dayOffRepository.findAllGlobalOrBranch(staff.getBranchId(), pageable);
        }
    }
}
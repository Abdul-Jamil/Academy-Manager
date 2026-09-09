package com.AjAkrampoor.Academy.staff.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StaffDeactivateUseCase {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public StaffDeactivateUseCase(StaffRepository staffRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Staff execute(String staffIdStr) {
        Staff staff = staffRepository.findById(StaffId.from(staffIdStr)).orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        if (!staff.isActive()) {
            return staff;
        }

        if (userRepository.findByStaffId(staff.getStaffId()).isPresent()) {
            User user = userRepository.findByStaffId(staff.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such user found"));
            Role role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));


            if (role.isSuperAdmin()) {
                throw new IllegalArgumentException("Cannot disable root admin staff");
            }
        }

        staff.deactivate();
        return staffRepository.save(staff);
    }
}

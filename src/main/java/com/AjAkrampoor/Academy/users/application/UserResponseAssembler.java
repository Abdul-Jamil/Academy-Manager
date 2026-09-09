package com.AjAkrampoor.Academy.users.application;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.dto.UserResponse;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserResponseAssembler {
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public UserResponseAssembler(RoleRepository roleRepository, StaffRepository staffRepository) {
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    public UserResponse toResponse(User user) {

        Role role = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("Role Not Found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff Not Found"));

        return new UserResponse(
                user.getUserId().asString(),
                user.getUserName().getValue(),
                user.getLanguage(),
                role.getName().getValue(),
                staff.getStatus(),
                role.getPermissions()
        );
    }
}

package com.AjAkrampoor.Academy.salaries.application;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SalaryAuthorizationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public SalaryAuthorizationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            StaffRepository staffRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    public Staff requireAccessibleStaff(
            String userId,
            StaffId targetStaffId
    ) {
        User user = userRepository.findById(
                        UserId.fromString(userId)
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Role not found")
                );

        Staff targetStaff = staffRepository.findById(targetStaffId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Staff not found")
                );

        if (!role.isSuperAdmin()) {

            Staff actorStaff = staffRepository.findById(
                            user.getStaffId()
                    )
                    .orElseThrow(() ->
                            new IllegalArgumentException("Staff record not found")
                    );

            if (!actorStaff.getBranchId().equals(
                    targetStaff.getBranchId()
            )) {
                throw new IllegalArgumentException(
                        "Cannot access other branches' salary data"
                );
            }
        }

        return targetStaff;
    }

    public User requireUser(String userId) {
        return userRepository.findById(
                        UserId.fromString(userId)
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );
    }
}
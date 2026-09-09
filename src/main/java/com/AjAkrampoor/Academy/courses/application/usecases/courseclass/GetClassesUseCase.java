package com.AjAkrampoor.Academy.courses.application.usecases.courseclass;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.courses.application.dto.ClassFilter;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
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
public class GetClassesUseCase {

    private final CourseClassRepository courseClassRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;

    public GetClassesUseCase(
            CourseClassRepository courseClassRepository,
            UserRepository userRepository,
            StaffRepository staffRepository,
            RoleRepository roleRepository
    ) {
        this.courseClassRepository = courseClassRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Page<CourseClass> execute(String userId, ClassFilter filter, Pageable pageable) {

        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        BranchId branchId = role.isSuperAdmin() ? null : staff.getBranchId();

        return courseClassRepository.findAll(filter, branchId, pageable);
    }
}

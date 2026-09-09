package com.AjAkrampoor.Academy.courses.application.usecases.courseclass;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UpdateClassDateRangeUseCase {

    private final CourseClassRepository courseClassRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;

    public UpdateClassDateRangeUseCase(
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

    @Transactional
    public CourseClass execute(String userId, UUID classId, LocalDate startDate) {

        CourseClass courseClass = courseClassRepository.findById(new ClassId(classId))
                .orElseThrow(() -> new IllegalArgumentException("No such class found"));

        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));

        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (!role.isSuperAdmin() && !staff.getBranchId().equals(courseClass.getBranchId())) {
            throw new IllegalStateException("Cannot access other branches' data");
        }

        if (courseClass.isCanceled() || courseClass.isFinished()) {
            throw new IllegalStateException("Cannot update dates of an inactive class");
        }

        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }

        courseClass.changeDateRange(startDate, null);

        return courseClassRepository.save(courseClass);
    }
}

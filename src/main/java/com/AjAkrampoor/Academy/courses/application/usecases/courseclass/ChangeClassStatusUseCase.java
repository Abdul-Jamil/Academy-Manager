package com.AjAkrampoor.Academy.courses.application.usecases.courseclass;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassStatus;
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
public class ChangeClassStatusUseCase {

    private final CourseClassRepository courseClassRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;

    public ChangeClassStatusUseCase(
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
    public CourseClass execute(
            String userId,
            UUID classId,
            ClassStatus newStatus,
            LocalDate startDate
    ) {
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

        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        if (newStatus == courseClass.getClassStatus()) {
            throw new IllegalArgumentException("Status is already set");
        }

        if (newStatus == ClassStatus.PENDING) {
            throw new IllegalArgumentException("Cannot change an existing class back to pending");
        }

        switch (newStatus) {
            case ACTIVE -> courseClass.activate(startDate);

            case CANCELED -> courseClass.cancel();

            case FINISHED -> {
                courseClass.finish();

                // A finished class gets its actual end date now.
                courseClass.changeDateRange(null, LocalDate.now());
            }

            case PENDING -> throw new IllegalArgumentException("Cannot change an existing class to pending");
        }

        return courseClassRepository.save(courseClass);
    }
}

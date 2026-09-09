package com.AjAkrampoor.Academy.courses.application;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassType;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TeacherAssignmentAccessService {

    private final CourseClassRepository courseClassRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public TeacherAssignmentAccessService(
            CourseClassRepository courseClassRepository,
            UserRepository userRepository,
            RoleRepository roleRepository,
            StaffRepository staffRepository
    ) {
        this.courseClassRepository = courseClassRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    public CourseClass getClassAndCheckAccess(
            String userId,
            UUID classId
    ) {
        CourseClass courseClass =
                courseClassRepository.findById(
                        new ClassId(classId)
                ).orElseThrow(
                        () -> new IllegalArgumentException(
                                "No such class found"
                        )
                );

        User user =
                userRepository.findById(
                        UserId.fromString(userId)
                ).orElseThrow(
                        () -> new IllegalArgumentException(
                                "No such user found"
                        )
                );

        Staff staff =
                staffRepository.findById(
                        user.getStaffId()
                ).orElseThrow(
                        () -> new IllegalArgumentException(
                                "No such staff found"
                        )
                );

        Role role =
                roleRepository.findById(
                        user.getRoleId()
                ).orElseThrow(
                        () -> new IllegalArgumentException(
                                "No such role found"
                        )
                );

        if (!role.isSuperAdmin()
                && !staff.getBranchId().equals(
                        courseClass.getBranchId()
                )) {

            throw new IllegalStateException(
                    "Cannot access other branches' data"
            );
        }

        if (courseClass.getClassType()
                != ClassType.FIXED_TEACHERS) {

            throw new IllegalStateException(
                    "Teacher assignments can only be used by FIXED_TEACHERS classes"
            );
        }

        return courseClass;
    }

    public Staff getTeacher(String teacherId) {
        return staffRepository.findById(
                        new StaffId(teacherId)
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Teacher not found"
                        )
                );
    }
}
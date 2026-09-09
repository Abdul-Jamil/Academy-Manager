package com.AjAkrampoor.Academy.enrollments.application.usecases;

import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateEnrollmentDescriptionUseCase {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final CourseClassRepository classRepository;
    private final RoleRepository roleRepository;

    public UpdateEnrollmentDescriptionUseCase(EnrollmentRepository enrollmentRepository, UserRepository userRepository, StaffRepository staffRepository, CourseClassRepository classRepository, RoleRepository roleRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.classRepository = classRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Enrollment execute(UUID enrollmentId, String description, String userId) {
        Enrollment enrollment = enrollmentRepository.findById(new EnrollmentId(enrollmentId)).orElseThrow(() -> new RuntimeException("No such enrollment found"));
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        CourseClass courseClass = classRepository.findById(enrollment.getClassId()).orElseThrow(() -> new IllegalArgumentException("No such class found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        if (!userRole.isSuperAdmin()) {
            if (!courseClass.getBranchId().equals(staff.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        if (description != null) {
            enrollment.updateDescription(new Description(description));
        }

        if (description == null) {
            enrollment.updateDescription(null);
        }

        return enrollmentRepository.save(enrollment);
    }
}

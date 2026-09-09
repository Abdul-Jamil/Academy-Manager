package com.AjAkrampoor.Academy.enrollments.application.usecases;

import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.enrollments.application.EnrollmentFilterConverter;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentFilter;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentSearchRequest;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetEnrollmentsUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CourseClassRepository classRepository;
    private final StaffRepository staffRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentFilterConverter enrollmentFilterConverter;

    public GetEnrollmentsUseCase(UserRepository userRepository, RoleRepository roleRepository, CourseClassRepository classRepository, StaffRepository staffRepository, EnrollmentRepository enrollmentRepository, EnrollmentFilterConverter enrollmentFilterConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.classRepository = classRepository;
        this.staffRepository = staffRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentFilterConverter = enrollmentFilterConverter;
    }

    public PaginatedResponse<Enrollment> execute(EnrollmentSearchRequest searchRequest, String userId, PaginationRequest paginationRequest) {
        EnrollmentFilter filter = enrollmentFilterConverter.fromRequest(searchRequest);
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        if (!userRole.isSuperAdmin()) {
            if (filter.getClassId() != null) {
                // Validate that the requested class belongs to user's branch
                CourseClass requestedClass = classRepository.findById(filter.getClassId())
                        .orElseThrow(() -> new IllegalArgumentException("Class not found"));

                if (!requestedClass.getBranchId().equals(staff.getBranchId())) {
                    throw new SecurityException("Cannot access other branch's class");
                }

            } else {
                filter.setBranchId(staff.getBranchId());
            }
        }

        Pageable pageable = paginationRequest.toPageable();

        Page<Enrollment> enrollments = enrollmentRepository.findAll(filter, pageable);

        return new PaginatedResponse<>(enrollments);
    }

}

// class ID not provided -> Get all enrollments - if not admin, show all from branch
// class ID provided -> Get class enrollments - if not admin, check branch

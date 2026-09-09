package com.AjAkrampoor.Academy.enrollments.application;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentFilter;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentSearchRequest;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentFilterConverter {

    private final UserRepository userRepository;

    private EnrollmentFilterConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public EnrollmentFilter fromRequest(EnrollmentSearchRequest request) {

        EnrollmentFilter filter = new EnrollmentFilter();

        if (request.getEnrollmentId() != null) {
            filter.setEnrollmentId(new EnrollmentId(request.getEnrollmentId()));
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            filter.setDescription(new Description(request.getDescription()));
        }

        filter.setDescriptionText(request.getDescriptionText());

        if (request.getStudentId() != null && !request.getStudentId().isBlank()) {
            filter.setStudentId(StudentId.from(request.getStudentId()));
        }

        if (request.getClassId() != null) {
            filter.setClassId(new ClassId(request.getClassId()));
        }

        filter.setEnrolledAfter(request.getEnrolledAfter());

        filter.setEnrolledBefore(request.getEnrolledBefore());

        if (request.getEnrolledBy() != null && !request.getEnrolledBy().isBlank()) {
            StaffId staffId = StaffId.from(request.getEnrolledBy());
            User user = userRepository.findByStaffId(staffId)
                    .orElseThrow(() -> new IllegalArgumentException("No user found for staff ID: " + staffId));
            filter.setEnrolledBy(user.getUserId());
        }

        filter.setStatus(request.getStatus());

        if (request.getTransferredTo() != null) {
            filter.setTransferredTo(new ClassId(request.getTransferredTo()));
        }

        filter.setTransferred(request.getTransferred());

        filter.setTransferredAfter(request.getTransferredAfter());

        filter.setTransferredBefore(request.getTransferredBefore());

        if (request.getTransferredBy() != null && !request.getTransferredBy().isBlank()) {
            StaffId staffId = StaffId.from(request.getTransferredBy());
            User user = userRepository.findByStaffId(staffId)
                    .orElseThrow(() -> new IllegalArgumentException("No user found for staff ID: " + staffId));
            filter.setTransferredBy(user.getUserId());
        }

        if (request.getCancelledBy() != null && !request.getCancelledBy().isBlank()) {
            StaffId staffId = StaffId.from(request.getCancelledBy());
            User user = userRepository.findByStaffId(staffId)
                    .orElseThrow(() -> new IllegalArgumentException("No user found for staff ID: " + staffId));
            filter.setCancelledBy(user.getUserId());
        }

        filter.setCancelledAfter(request.getCancelledAfter());

        filter.setCancelledBefore(request.getCancelledBefore());

        if (request.getBranchId() != null) {
            filter.setBranchId(new BranchId(request.getBranchId()));
        }

        return filter;
    }
}

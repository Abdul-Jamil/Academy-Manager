package com.AjAkrampoor.Academy.enrollments.application;

import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentResponse;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentResponseAssembler {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final StudentRepository studentRepository;

    public EnrollmentResponseAssembler(UserRepository userRepository, StaffRepository staffRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.studentRepository = studentRepository;
    }

    public EnrollmentResponse toResponse(Enrollment enrollment) {

        Student student = studentRepository.findById(enrollment.getStudentId()).orElseThrow(() -> new IllegalArgumentException("Student not found"));

        String cancelledBy = null;
        if (enrollment.getCancelledBy() != null) {
            User user = userRepository.findById(enrollment.getCancelledBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            cancelledBy = staff.getName().getFullName();
        }

        String transferredBy = null;
        if (enrollment.getTransferredBy() != null) {
            User user = userRepository.findById(enrollment.getTransferredBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            transferredBy = staff.getName().getFullName();
        }

        String enrolledBy = null;
        if (enrollment.getEnrolledBy() != null) {
            User user = userRepository.findById(enrollment.getEnrolledBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            enrolledBy = staff.getName().getFullName();
        }

        return new EnrollmentResponse(
                enrollment.getEnrollmentId().toString(),
                enrollment.getDescription() != null ? enrollment.getDescription().getValue() : null,
                student.getStudentId().getId(),
                student.getStudentName().getFullName(),
                enrollment.getClassId().toString(),
                enrollment.getEnrolledAt(),
                enrolledBy,
                enrollment.getEnrollmentStatus(),
                enrollment.getTransferredTo() != null ? enrollment.getTransferredTo().toString() : null,
                enrollment.getTransferredAt(),
                cancelledBy,
                enrollment.getCancelledAt(),
                transferredBy
        );
    }
}

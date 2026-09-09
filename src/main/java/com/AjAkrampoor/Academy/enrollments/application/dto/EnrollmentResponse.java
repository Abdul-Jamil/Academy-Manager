package com.AjAkrampoor.Academy.enrollments.application.dto;

import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentStatus;

import java.time.LocalDate;

public class EnrollmentResponse {
    private String enrollmentId;
    private String description;
    private String studentId;
    private String studentName;
    private String classId;
    private LocalDate enrolledAt;
    private String enrolledBy;
    private EnrollmentStatus enrollmentStatus;
    private String transferredTo;
    private LocalDate transferredAt;
    private String cancelledBy;
    private LocalDate cancelledAt;
    private String transferredBy;

    public EnrollmentResponse(String enrollmentId,
                              String description,
                              String studentId,
                              String studentName,
                              String classId,
                              LocalDate enrolledAt,
                              String enrolledBy,
                              EnrollmentStatus enrollmentStatus,
                              String transferredTo,
                              LocalDate transferredAt,
                              String cancelledBy,
                              LocalDate cancelledAt,
                              String transferredBy) {
        this.enrollmentId = enrollmentId;
        this.description = description;
        this.studentId = studentId;
        this.studentName = studentName;
        this.classId = classId;
        this.enrolledAt = enrolledAt;
        this.enrolledBy = enrolledBy;
        this.enrollmentStatus = enrollmentStatus;
        this.transferredTo = transferredTo;
        this.transferredAt = transferredAt;
        this.cancelledBy = cancelledBy;
        this.cancelledAt = cancelledAt;
        this.transferredBy = transferredBy;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public String getDescription() {
        return description;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getClassId() {
        return classId;
    }

    public LocalDate getEnrolledAt() {
        return enrolledAt;
    }

    public String getEnrolledBy() {
        return enrolledBy;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public String getTransferredTo() {
        return transferredTo;
    }

    public LocalDate getTransferredAt() {
        return transferredAt;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public LocalDate getCancelledAt() {
        return cancelledAt;
    }

    public String getTransferredBy() {
        return transferredBy;
    }
}

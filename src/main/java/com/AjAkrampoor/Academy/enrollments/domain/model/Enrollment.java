package com.AjAkrampoor.Academy.enrollments.domain.model;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDate;
import java.util.Objects;

public class Enrollment {
    private EnrollmentId enrollmentId;
    private Description description;
    private StudentId studentId;
    private ClassId classId;
    private LocalDate enrolledAt;
    private UserId enrolledBy;
    private EnrollmentStatus enrollmentStatus;
    private ClassId transferredTo;
    private LocalDate transferredAt;
    private UserId cancelledBy;
    private LocalDate cancelledAt;
    private UserId transferredBy;

    public Enrollment(EnrollmentId enrollmentId, Description description, StudentId studentId, ClassId classId, LocalDate enrolledAt, UserId enrolledBy, EnrollmentStatus enrollmentStatus, ClassId transferredTo, LocalDate transferredAt, UserId cancelledBy, LocalDate cancelledAt, UserId transferredBy) {
        this.enrollmentId = enrollmentId;
        this.description = description;
        this.studentId = studentId;
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

    public Enrollment(EnrollmentId enrollmentId, Description description, StudentId studentId, ClassId classId, LocalDate enrolledAt, UserId enrolledBy, EnrollmentStatus enrollmentStatus) {
        this.enrollmentId = Objects.requireNonNull(enrollmentId, "Enrollment ID must not be null");
        this.description = description;
        this.studentId = Objects.requireNonNull(studentId, "Student ID must not be null");
        this.classId = Objects.requireNonNull(classId, "Class ID must not be null");
        this.enrolledAt = Objects.requireNonNull(enrolledAt, "Enrolled At must not be null");
        this.enrolledBy = Objects.requireNonNull(enrolledBy, "User ID must not be null");
        this.enrollmentStatus = Objects.requireNonNull(enrollmentStatus, "Enrollment Status must not be null");
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public void transfer(ClassId newClass, UserId transferredBy) {
        if (!this.enrollmentStatus.equals(EnrollmentStatus.ENROLLED)) {
            throw new IllegalArgumentException("Enrollment Status must be ENROLLED");
        }
        if (newClass == null) {
            throw new IllegalArgumentException("Class ID must not be null");
        }

        if (newClass.equals(this.classId)) {
            throw new IllegalArgumentException("New class is the same as the current class");
        }

        this.transferredTo = newClass;
        this.transferredBy = transferredBy;
        this.transferredAt = LocalDate.now();
        this.enrollmentStatus = EnrollmentStatus.TRANSFERRED;
    }

    public void cancel(UserId cancelledBy) {
        if (!this.enrollmentStatus.equals(EnrollmentStatus.ENROLLED)) {
            throw new IllegalArgumentException("Enrollment Status must be ENROLLED");
        }
        if (cancelledBy == null) {
            throw new IllegalArgumentException("Cancelled by must not be null");
        }

        this.enrollmentStatus = EnrollmentStatus.CANCELLED;
        this.cancelledBy = cancelledBy;
        this.cancelledAt = LocalDate.now();
    }

    public boolean isEnrolled() {
        return this.enrollmentStatus == EnrollmentStatus.ENROLLED;
    }

    public boolean isCancelled() {
        return this.enrollmentStatus == EnrollmentStatus.CANCELLED;
    }

    public boolean isTransferred() {
        return this.enrollmentStatus == EnrollmentStatus.TRANSFERRED;
    }

    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public Description getDescription() {
        return description;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public ClassId getClassId() {
        return classId;
    }

    public LocalDate getEnrolledAt() {
        return enrolledAt;
    }

    public UserId getEnrolledBy() {
        return enrolledBy;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public ClassId getTransferredTo() {
        return transferredTo;
    }

    public LocalDate getTransferredAt() {
        return transferredAt;
    }

    public UserId getCancelledBy() {
        return cancelledBy;
    }

    public LocalDate getCancelledAt() {
        return cancelledAt;
    }

    public UserId getTransferredBy() {
        return transferredBy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(enrollmentId, that.enrollmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(enrollmentId);
    }
}

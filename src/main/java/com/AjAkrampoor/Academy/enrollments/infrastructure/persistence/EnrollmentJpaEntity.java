package com.AjAkrampoor.Academy.enrollments.infrastructure.persistence;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity()
@Table(name = "enrollments")
public class EnrollmentJpaEntity {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", unique = true, nullable = false))
    private EnrollmentId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private Description description;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "student_id", nullable = false))
    private StudentId studentId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "class_id", nullable = false))
    private ClassId classId;

    @Column(nullable = false)
    private LocalDate enrolledAt;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "enrolled_by", nullable = false))
    private UserId enrolledBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus enrollmentStatus;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "transferred_to"))
    private ClassId transferredTo;

    private LocalDate transferredAt;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "cancelled_by", nullable = true))
    private UserId cancelledBy;

    private LocalDate cancelledAt;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "transferred_by"))
    private UserId transferredBy;

    public EnrollmentJpaEntity() {
    }

    public EnrollmentId getId() {
        return id;
    }

    public void setId(EnrollmentId id) {
        this.id = id;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public void setStudentId(StudentId studentId) {
        this.studentId = studentId;
    }

    public ClassId getClassId() {
        return classId;
    }

    public void setClassId(ClassId classId) {
        this.classId = classId;
    }

    public LocalDate getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDate enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public UserId getEnrolledBy() {
        return enrolledBy;
    }

    public void setEnrolledBy(UserId enrolledBy) {
        this.enrolledBy = enrolledBy;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public ClassId getTransferredTo() {
        return transferredTo;
    }

    public void setTransferredTo(ClassId transferredTo) {
        this.transferredTo = transferredTo;
    }

    public LocalDate getTransferredAt() {
        return transferredAt;
    }

    public void setTransferredAt(LocalDate transferredAt) {
        this.transferredAt = transferredAt;
    }

    public UserId getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(UserId cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public LocalDate getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDate cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public UserId getTransferredBy() {
        return transferredBy;
    }

    public void setTransferredBy(UserId transferredBy) {
        this.transferredBy = transferredBy;
    }
}

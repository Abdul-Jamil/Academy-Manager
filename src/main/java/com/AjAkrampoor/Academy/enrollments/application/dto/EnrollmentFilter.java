package com.AjAkrampoor.Academy.enrollments.application.dto;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDate;

public class EnrollmentFilter {

    private EnrollmentId enrollmentId;
    private Description description;
    private String descriptionText;
    private StudentId studentId;
    private ClassId classId;
    private LocalDate enrolledAfter;
    private LocalDate enrolledBefore;
    private UserId enrolledBy;
    private EnrollmentStatus status;
    private ClassId transferredTo;
    private Boolean transferred;
    private LocalDate transferredAfter;
    private LocalDate transferredBefore;
    private UserId transferredBy;
    private UserId cancelledBy;
    private LocalDate cancelledAfter;
    private LocalDate cancelledBefore;
    private BranchId branchId;

    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(EnrollmentId enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
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

    public LocalDate getEnrolledAfter() {
        return enrolledAfter;
    }

    public void setEnrolledAfter(LocalDate enrolledAfter) {
        this.enrolledAfter = enrolledAfter;
    }

    public LocalDate getEnrolledBefore() {
        return enrolledBefore;
    }

    public void setEnrolledBefore(LocalDate enrolledBefore) {
        this.enrolledBefore = enrolledBefore;
    }

    public UserId getEnrolledBy() {
        return enrolledBy;
    }

    public void setEnrolledBy(UserId enrolledBy) {
        this.enrolledBy = enrolledBy;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public ClassId getTransferredTo() {
        return transferredTo;
    }

    public void setTransferredTo(ClassId transferredTo) {
        this.transferredTo = transferredTo;
    }

    public Boolean getTransferred() {
        return transferred;
    }

    public void setTransferred(Boolean transferred) {
        this.transferred = transferred;
    }

    public LocalDate getTransferredAfter() {
        return transferredAfter;
    }

    public void setTransferredAfter(LocalDate transferredAfter) {
        this.transferredAfter = transferredAfter;
    }

    public LocalDate getTransferredBefore() {
        return transferredBefore;
    }

    public void setTransferredBefore(LocalDate transferredBefore) {
        this.transferredBefore = transferredBefore;
    }

    public UserId getTransferredBy() {
        return transferredBy;
    }

    public void setTransferredBy(UserId transferredBy) {
        this.transferredBy = transferredBy;
    }

    public UserId getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(UserId cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public LocalDate getCancelledAfter() {
        return cancelledAfter;
    }

    public void setCancelledAfter(LocalDate cancelledAfter) {
        this.cancelledAfter = cancelledAfter;
    }

    public LocalDate getCancelledBefore() {
        return cancelledBefore;
    }

    public void setCancelledBefore(LocalDate cancelledBefore) {
        this.cancelledBefore = cancelledBefore;
    }

    public boolean isEmpty() {
        return enrollmentId == null
                && description == null
                && (descriptionText == null || descriptionText.isBlank())
                && studentId == null
                && classId == null
                && enrolledAfter == null
                && enrolledBefore == null
                && enrolledBy == null
                && status == null
                && transferredTo == null
                && transferred == null
                && transferredAfter == null
                && transferredBefore == null
                && transferredBy == null
                && cancelledBy == null
                && cancelledAfter == null
                && cancelledBefore == null;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchId branchId) {
        this.branchId = branchId;
    }
}



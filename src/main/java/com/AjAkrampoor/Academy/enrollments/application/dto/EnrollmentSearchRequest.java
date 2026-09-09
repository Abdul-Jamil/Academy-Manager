package com.AjAkrampoor.Academy.enrollments.application.dto;

import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentStatus;

import java.time.LocalDate;
import java.util.UUID;

public class EnrollmentSearchRequest {
    private UUID enrollmentId;
    private String description;
    private String descriptionText;
    private String studentId;
    private UUID classId;

    private LocalDate enrolledAfter;
    private LocalDate enrolledBefore;
    private String enrolledBy;

    private EnrollmentStatus status;

    private UUID transferredTo;
    private Boolean transferred;
    private LocalDate transferredAfter;
    private LocalDate transferredBefore;
    private String transferredBy;

    private String cancelledBy;
    private LocalDate cancelledAfter;
    private LocalDate cancelledBefore;

    private UUID branchId;
    private UUID billId;
    private Boolean hasBill;

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
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

    public String getEnrolledBy() {
        return enrolledBy;
    }

    public void setEnrolledBy(String enrolledBy) {
        this.enrolledBy = enrolledBy;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public UUID getTransferredTo() {
        return transferredTo;
    }

    public void setTransferredTo(UUID transferredTo) {
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

    public String getTransferredBy() {
        return transferredBy;
    }

    public void setTransferredBy(String transferredBy) {
        this.transferredBy = transferredBy;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
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

    public UUID getBillId() {
        return billId;
    }

    public void setBillId(UUID billId) {
        this.billId = billId;
    }

    public Boolean getHasBill() {
        return hasBill;
    }

    public void setHasBill(Boolean hasBill) {
        this.hasBill = hasBill;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }
}

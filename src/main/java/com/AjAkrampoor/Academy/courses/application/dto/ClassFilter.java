package com.AjAkrampoor.Academy.courses.application.dto;

import com.AjAkrampoor.Academy.courses.domain.model.ClassStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ClassFilter {

    private String description;
    private UUID branchId;

    private LocalDate startDateFrom;
    private LocalDate startDateTo;

    private LocalDate endDateFrom;
    private LocalDate endDateTo;

    private BigDecimal feeMin;
    private BigDecimal feeMax;

    private ClassStatus status;

    private boolean overdue;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }

    public LocalDate getStartDateFrom() {
        return startDateFrom;
    }

    public void setStartDateFrom(LocalDate startDateFrom) {
        this.startDateFrom = startDateFrom;
    }

    public LocalDate getStartDateTo() {
        return startDateTo;
    }

    public void setStartDateTo(LocalDate startDateTo) {
        this.startDateTo = startDateTo;
    }

    public LocalDate getEndDateFrom() {
        return endDateFrom;
    }

    public void setEndDateFrom(LocalDate endDateFrom) {
        this.endDateFrom = endDateFrom;
    }

    public LocalDate getEndDateTo() {
        return endDateTo;
    }

    public void setEndDateTo(LocalDate endDateTo) {
        this.endDateTo = endDateTo;
    }

    public BigDecimal getFeeMin() {
        return feeMin;
    }

    public void setFeeMin(BigDecimal feeMin) {
        this.feeMin = feeMin;
    }

    public BigDecimal getFeeMax() {
        return feeMax;
    }

    public void setFeeMax(BigDecimal feeMax) {
        this.feeMax = feeMax;
    }

    public ClassStatus getStatus() {
        return status;
    }

    public void setStatus(ClassStatus status) {
        this.status = status;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}

package com.AjAkrampoor.Academy.courses.application.dto;

import com.AjAkrampoor.Academy.courses.domain.model.ClassType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ClassCreateRequest {

    @Min(value = 1, message = "Duration cannot be less than 1")
    private int duration;

    private LocalDate startDate;

    @NotNull(message = "Description cannot be null")
    @Size(min = 3, max = 200)
    private String description;

    @NotNull(message = "Fee cannot be null")
    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal fee;

    @NotNull(message = "Class Type cannot be null")
    private ClassType classType;

    @NotNull(message = "Branch ID cannot be null")
    private UUID branchId;

    private boolean pending;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}

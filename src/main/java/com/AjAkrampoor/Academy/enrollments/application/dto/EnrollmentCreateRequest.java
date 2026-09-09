package com.AjAkrampoor.Academy.enrollments.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public class EnrollmentCreateRequest {

    @NotNull(message = "Student ID cannot be null")
    private String studentId;
    @NotNull(message = "Class ID cannot be null")
    private UUID classId;
    @Size(min = 3, max = 200, message = "Description must be between 3 and 200 characters")
    private String description;

    @DecimalMin(value = "0.0", message = "Discount must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Discount must have up to 10 integer digits and 2 decimal places")
    private BigDecimal discount = BigDecimal.ZERO;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}

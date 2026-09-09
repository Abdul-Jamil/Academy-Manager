package com.AjAkrampoor.Academy.courses.application.dto;

import com.AjAkrampoor.Academy.courses.domain.model.ClassStatus;
import com.AjAkrampoor.Academy.courses.domain.model.ClassType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClassResponse {

    private String id;
    private int durationDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private BigDecimal fee;
    private ClassType classType;
    private int enrolledStudentsCount;
    private ClassStatus classStatus;
    private String branchName;

    public ClassResponse(
            String id,
            int durationDays,
            LocalDate startDate,
            LocalDate endDate,
            String description,
            BigDecimal fee,
            ClassType classType,
            int enrolledStudentsCount,
            ClassStatus classStatus,
            String branchName
    ) {
        this.id = id;
        this.durationDays = durationDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.fee = fee;
        this.enrolledStudentsCount = enrolledStudentsCount;
        this.classStatus = classStatus;
        this.branchName = branchName;
    }

    public String getId() {
        return id;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public ClassType getClassType() {
        return classType;
    }

    public int getEnrolledStudentsCount() {
        return enrolledStudentsCount;
    }

    public ClassStatus getClassStatus() {
        return classStatus;
    }

    public String getBranchName() {
        return branchName;
    }
}

package com.AjAkrampoor.Academy.sessions.application.dto;

import com.AjAkrampoor.Academy.sessions.domain.model.DayOffStatus;

import java.time.LocalDate;

public class DayOffResponse {
    private String dayOffId;
    private LocalDate date;
    private String description;
    private DayOffStatus status;
    private String createdBy;
    private String branchId;

    public DayOffResponse(String dayOffId, LocalDate date, String description, DayOffStatus status, String createdBy, String branchId) {
        this.dayOffId = dayOffId;
        this.date = date;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.branchId = branchId;
    }

    public String getDayOffId() {
        return dayOffId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public DayOffStatus getStatus() {
        return status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getBranchId() {
        return branchId;
    }
}

package com.AjAkrampoor.Academy.sessions.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class CreateSessionRequest {

    @NotNull(message = "Schedule entry ID cannot be null")
    private UUID scheduleEntryId;

    private String teacherId;

    private LocalDate sessionDate;


    @Size(min = 3, max = 200, message = "Description must be between 3 and 200 characters")
    private String description;

    public UUID getScheduleEntryId() {
        return scheduleEntryId;
    }

    public void setScheduleEntryId(UUID scheduleEntryId) {
        this.scheduleEntryId = scheduleEntryId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

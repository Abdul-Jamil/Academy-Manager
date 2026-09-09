package com.AjAkrampoor.Academy.sessions.application.dto;

import com.AjAkrampoor.Academy.sessions.domain.model.SessionStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionResponse {

    private String sessionId;
    private String scheduleEntryId;
    private String classDescription;
    private String teacherName;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private SessionStatus status;
    private String description;

    public SessionResponse(
            String sessionId,
            String scheduleEntryId,
            String classDescription,
            String teacherName,
            LocalDate sessionDate,
            LocalTime startTime,
            LocalTime endTime,
            SessionStatus status,
            String description
    ) {
        this.sessionId = sessionId;
        this.scheduleEntryId = scheduleEntryId;
        this.classDescription = classDescription;
        this.teacherName = teacherName;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.description = description;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getScheduleEntryId() {
        return scheduleEntryId;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}

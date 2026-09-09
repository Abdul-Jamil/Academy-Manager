package com.AjAkrampoor.Academy.courses.application.dto;

import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class TeacherAssignmentResponse {

    private String id;
    private String classId;
    private String teacherId;
    private String description;

    private Set<DayOfWeek> daysOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    private TeacherAssignmentStatus status;

    public TeacherAssignmentResponse(
            String id,
            String classId,
            String teacherId,
            String description,
            Set<DayOfWeek> daysOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            TeacherAssignmentStatus status
    ) {
        this.id = id;
        this.classId = classId;
        this.teacherId = teacherId;
        this.description = description;
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getClassId() {
        return classId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getDescription() {
        return description;
    }

    public Set<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public TeacherAssignmentStatus getStatus() {
        return status;
    }
}
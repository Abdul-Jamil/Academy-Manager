package com.AjAkrampoor.Academy.courses.application.dto;

import com.AjAkrampoor.Academy.courses.domain.model.ScheduleStatus;

import java.time.LocalDate;
import java.util.List;

public class ClassScheduleResponse {

    private String id;
    private String classId;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private ScheduleStatus status;
    private List<ScheduleEntryResponse> entries;

    public ClassScheduleResponse(
            String id,
            String classId,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            ScheduleStatus status,
            List<ScheduleEntryResponse> entries
    ) {
        this.id = id;
        this.classId = classId;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.status = status;
        this.entries = entries;
    }

    public String getId() {
        return id;
    }

    public String getClassId() {
        return classId;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public List<ScheduleEntryResponse> getEntries() {
        return entries;
    }
}

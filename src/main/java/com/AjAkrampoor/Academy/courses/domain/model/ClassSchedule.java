package com.AjAkrampoor.Academy.courses.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class ClassSchedule {

    private ClassScheduleId id;
    private ClassId classId;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private ScheduleStatus status;

    public ClassSchedule(
            ClassScheduleId id,
            ClassId classId,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            ScheduleStatus status
    ) {
        this.id = Objects.requireNonNull(id, "Schedule ID must not be null");
        this.classId = Objects.requireNonNull(classId, "Class ID must not be null");
        this.status = Objects.requireNonNull(status, "Schedule status must not be null");

        validateDates(effectiveFrom, effectiveTo);

        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
    }

    public void updateEffectivePeriod(LocalDate effectiveFrom, LocalDate effectiveTo) {
        validateDates(effectiveFrom, effectiveTo);

        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
    }

    public void activate() {
        this.status = ScheduleStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = ScheduleStatus.INACTIVE;
    }

    public boolean isActive() {
        return this.status == ScheduleStatus.ACTIVE;
    }

    private void validateDates(LocalDate effectiveFrom, LocalDate effectiveTo) {
        if (effectiveTo != null && effectiveFrom == null) {
            throw new IllegalArgumentException(
                    "Effective from date is required when effective to date is provided"
            );
        }

        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new IllegalArgumentException("Effective to date cannot be before effective from date");
        }
    }

    public ClassScheduleId getId() {
        return id;
    }

    public ClassId getClassId() {
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
}

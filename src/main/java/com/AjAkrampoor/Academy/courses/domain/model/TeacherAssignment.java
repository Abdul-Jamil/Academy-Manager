package com.AjAkrampoor.Academy.courses.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TeacherAssignment {

    private TeacherAssignmentId id;
    private ClassId classId;
    private StaffId teacherId;
    private Description description;

    private Set<DayOfWeek> daysOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    private TeacherAssignmentStatus status;

    public TeacherAssignment(
            TeacherAssignmentId id,
            ClassId classId,
            StaffId teacherId,
            Description description,
            Set<DayOfWeek> daysOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            TeacherAssignmentStatus status
    ) {
        this.id = Objects.requireNonNull(
                id,
                "Teacher assignment ID cannot be null"
        );

        this.classId = Objects.requireNonNull(
                classId,
                "Class ID cannot be null"
        );

        this.teacherId = Objects.requireNonNull(
                teacherId,
                "Teacher ID cannot be null"
        );

        this.description = Objects.requireNonNull(
                description,
                "Description cannot be null"
        );

        validateDays(daysOfWeek);
        validateTimeRange(startTime, endTime);
        validateDates(effectiveFrom, effectiveTo);

        this.daysOfWeek = new HashSet<>(daysOfWeek);
        this.startTime = startTime;
        this.endTime = endTime;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;

        this.status = Objects.requireNonNull(
                status,
                "Status cannot be null"
        );
    }

    public void update(
            StaffId teacherId,
            Description description,
            Set<DayOfWeek> daysOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            LocalDate effectiveFrom,
            LocalDate effectiveTo
    ) {
        this.teacherId = Objects.requireNonNull(
                teacherId,
                "Teacher ID cannot be null"
        );

        this.description = Objects.requireNonNull(
                description,
                "Description cannot be null"
        );

        validateDays(daysOfWeek);
        validateTimeRange(startTime, endTime);
        validateDates(effectiveFrom, effectiveTo);

        this.daysOfWeek = new HashSet<>(daysOfWeek);
        this.startTime = startTime;
        this.endTime = endTime;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
    }

    public void activate() {
        if (this.status == TeacherAssignmentStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Teacher assignment is already active"
            );
        }

        this.status = TeacherAssignmentStatus.ACTIVE;
    }

    public void deactivate() {
        if (this.status == TeacherAssignmentStatus.INACTIVE) {
            throw new IllegalStateException(
                    "Teacher assignment is already inactive"
            );
        }

        this.status = TeacherAssignmentStatus.INACTIVE;
    }

    private void validateDays(Set<DayOfWeek> daysOfWeek) {
        if (daysOfWeek == null || daysOfWeek.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one day must be selected"
            );
        }
    }

    private void validateTimeRange(
            LocalTime startTime,
            LocalTime endTime
    ) {
        if (startTime == null && endTime == null) {
            return;
        }

        if (startTime == null) {
            throw new IllegalArgumentException(
                    "Start time is required when end time is provided"
            );
        }

        if (endTime == null) {
            throw new IllegalArgumentException(
                    "End time is required when start time is provided"
            );
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }
    }

    private void validateDates(
            LocalDate effectiveFrom,
            LocalDate effectiveTo
    ) {
        if (effectiveTo != null && effectiveFrom == null) {
            throw new IllegalArgumentException(
                    "Effective from date is required when effective to date is provided"
            );
        }

        if (effectiveFrom != null
                && effectiveTo != null
                && effectiveTo.isBefore(effectiveFrom)) {

            throw new IllegalArgumentException(
                    "Effective to date cannot be before effective from date"
            );
        }
    }

    public TeacherAssignmentId getId() {
        return id;
    }

    public ClassId getClassId() {
        return classId;
    }

    public StaffId getTeacherId() {
        return teacherId;
    }

    public Description getDescription() {
        return description;
    }

    public Set<DayOfWeek> getDaysOfWeek() {
        return Collections.unmodifiableSet(daysOfWeek);
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

    public boolean isActive() {
        return status == TeacherAssignmentStatus.ACTIVE;
    }

    public boolean isInactive() {
        return status == TeacherAssignmentStatus.INACTIVE;
    }
}
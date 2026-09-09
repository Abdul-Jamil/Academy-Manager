package com.AjAkrampoor.Academy.courses.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;

import java.time.LocalDate;
import java.util.Objects;

public class CourseClass {

    private ClassId id;
    private DurationDays duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private Description description;
    private Money fee;
    private ClassType classType;
    private ClassStatus classStatus;
    private BranchId branchId;

    public CourseClass(
            ClassId id,
            DurationDays duration,
            LocalDate startDate,
            LocalDate endDate,
            Description description,
            Money fee, ClassType classType,
            ClassStatus classStatus,
            BranchId branchId
    ) {
        this.classType = classType;
        validateDates(startDate, endDate);

        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.duration = Objects.requireNonNull(duration, "Duration days must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.fee = Objects.requireNonNull(fee, "Fee must not be null");
        this.classStatus = Objects.requireNonNull(classStatus, "Class status must not be null");
        this.branchId = Objects.requireNonNull(branchId, "Branch ID must not be null");
    }

    public void updateDurationDays(DurationDays duration) {
        if (duration == null) {
            throw new IllegalArgumentException("Duration cannot be null");
        }

        this.duration = duration;
    }

    public void changeDateRange(LocalDate newStartDate, LocalDate newEndDate) {
        LocalDate finalStart = newStartDate != null ? newStartDate : this.startDate;

        LocalDate finalEnd = newEndDate != null ? newEndDate : this.endDate;

        validateDates(finalStart, finalEnd);

        this.startDate = finalStart;
        this.endDate = finalEnd;
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public void activate(LocalDate startDate) {
        if (this.classStatus == ClassStatus.CANCELED) {
            throw new IllegalStateException("Cannot activate a canceled class");
        }

        if (this.classStatus == ClassStatus.FINISHED) {
            throw new IllegalStateException("Cannot activate a finished class");
        }

        if (this.startDate == null) {
            this.startDate = Objects.requireNonNull(startDate, "Start date is required when activating a class");
        }

        this.classStatus = ClassStatus.ACTIVE;
    }

    public void cancel() {
        if (this.classStatus == ClassStatus.FINISHED) {
            throw new IllegalStateException("Cannot cancel a finished class");
        }

        this.classStatus = ClassStatus.CANCELED;
    }

    public void finish() {
        if (this.classStatus == ClassStatus.CANCELED) {
            throw new IllegalStateException("Cannot finish a canceled class");
        }

        if (this.classStatus == ClassStatus.PENDING) {
            throw new IllegalStateException("Cannot finish a pending class");
        }

        this.classStatus = ClassStatus.FINISHED;
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && startDate == null) {
            throw new IllegalArgumentException("Start date is required when end date is provided");
        }

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }

    public boolean isPending() {
        return this.classStatus == ClassStatus.PENDING;
    }

    public boolean isActive() {
        return this.classStatus == ClassStatus.ACTIVE;
    }

    public boolean isCanceled() {
        return this.classStatus == ClassStatus.CANCELED;
    }

    public boolean isFinished() {
        return this.classStatus == ClassStatus.FINISHED;
    }

    public ClassId getId() {
        return id;
    }

    public DurationDays getDuration() {
        return duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Description getDescription() {
        return description;
    }

    public Money getFee() {
        return fee;
    }

    public ClassType getClassType() {
        return classType;
    }

    public ClassStatus getClassStatus() {
        return classStatus;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public boolean isScheduled() {
        return classType == ClassType.SCHEDULED;
    }

    public boolean usesFixedTeachers() {
        return classType == ClassType.FIXED_TEACHERS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseClass that = (CourseClass) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

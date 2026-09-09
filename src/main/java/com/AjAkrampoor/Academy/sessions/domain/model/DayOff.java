package com.AjAkrampoor.Academy.sessions.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDate;
import java.util.Objects;

public class DayOff {
    private DayOffId dayOffId;
    private LocalDate date;
    private Description description;
    private DayOffStatus status;
    private UserId createdBy;
    private BranchId branchId;

    public DayOff(DayOffId dayOffId, LocalDate date, Description description, DayOffStatus status, UserId createdBy, BranchId branchId) {
        this.dayOffId = Objects.requireNonNull(dayOffId, "DayOffId cannot be null");
        this.date = Objects.requireNonNull(date, "Date cannot be null");
        this.description = description;
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.createdBy = Objects.requireNonNull(createdBy, "CreatedBy cannot be null");
        this.branchId = branchId;
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public void delete() {
        if (this.status == DayOffStatus.INACTIVE) {
            throw new IllegalArgumentException("Status is already inactive");
        }

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        if (this.date.isBefore(tomorrow)) {
            throw new IllegalStateException("Cannot delete a day off scheduled for today or earlier. Only future dates can be deleted.");
        }

        this.status = DayOffStatus.INACTIVE;
    }

    public void restore() {
        if (this.status == DayOffStatus.ACTIVE) {
            throw new IllegalStateException("DayOff is already active.");
        }

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        if (this.date.isBefore(tomorrow)) {
            throw new IllegalStateException("Cannot restore a day off scheduled for today or earlier. Only future dates can be restored.");
        }

        this.status = DayOffStatus.ACTIVE;
    }

    public DayOffId getDayOffId() {
        return dayOffId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Description getDescription() {
        return description;
    }

    public DayOffStatus getStatus() {
        return status;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public boolean isActive() {
        return this.status == DayOffStatus.ACTIVE;
    }
}

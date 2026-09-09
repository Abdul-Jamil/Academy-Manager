package com.AjAkrampoor.Academy.sessions.infrastructure.persistence;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOffId;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOffStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "day_off")
public class DayOffJpaEntity {
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(nullable = false))
    private DayOffId dayOffId;

    @Column(nullable = false, updatable = false)
    private LocalDate date;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description", nullable = false))
    private Description description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DayOffStatus status;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "created_by", nullable = false))
    private UserId createdBy;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "branch_id"))
    private BranchId branchId;

    public DayOffId getDayOffId() {
        return dayOffId;
    }

    public void setDayOffId(DayOffId dayOffId) {
        this.dayOffId = dayOffId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public DayOffStatus getStatus() {
        return status;
    }

    public void setStatus(DayOffStatus status) {
        this.status = status;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchId branchId) {
        this.branchId = branchId;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserId createdBy) {
        this.createdBy = createdBy;
    }
}

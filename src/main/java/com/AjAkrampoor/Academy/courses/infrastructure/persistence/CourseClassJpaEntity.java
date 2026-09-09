package com.AjAkrampoor.Academy.courses.infrastructure.persistence;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassStatus;
import com.AjAkrampoor.Academy.courses.domain.model.ClassType;
import com.AjAkrampoor.Academy.courses.domain.model.DurationDays;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "classes")
public class CourseClassJpaEntity {

    @EmbeddedId
    private ClassId id;

    @Embedded
    private DurationDays duration;

    private LocalDate startDate;

    private LocalDate endDate;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "description")
    )
    private Description description;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(name = "fee", nullable = false)
    )
    private Money fee;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_status", nullable = false)
    private ClassStatus classStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_type", nullable = false)
    private ClassType classType;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "branch_id", nullable = false)
    )
    private BranchId branchId;

    public CourseClassJpaEntity() {
    }

    public ClassId getId() {
        return id;
    }

    public void setId(ClassId id) {
        this.id = id;
    }

    public DurationDays getDuration() {
        return duration;
    }

    public void setDuration(DurationDays duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Money getFee() {
        return fee;
    }

    public void setFee(Money fee) {
        this.fee = fee;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public ClassStatus getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(ClassStatus classStatus) {
        this.classStatus = classStatus;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchId branchId) {
        this.branchId = branchId;
    }
}

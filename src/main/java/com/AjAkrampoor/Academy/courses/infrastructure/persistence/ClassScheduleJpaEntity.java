package com.AjAkrampoor.Academy.courses.infrastructure.persistence;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassScheduleId;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "class_schedules")
public class ClassScheduleJpaEntity {

    @EmbeddedId
    private ClassScheduleId id;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "class_id", nullable = false)
    )
    private ClassId classId;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status;

    public ClassScheduleJpaEntity() {
    }

    public ClassScheduleId getId() {
        return id;
    }

    public void setId(ClassScheduleId id) {
        this.id = id;
    }

    public ClassId getClassId() {
        return classId;
    }

    public void setClassId(ClassId classId) {
        this.classId = classId;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }
}

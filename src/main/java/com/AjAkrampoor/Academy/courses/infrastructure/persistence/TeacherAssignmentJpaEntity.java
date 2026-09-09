package com.AjAkrampoor.Academy.courses.infrastructure.persistence;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentId;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "teacher_assignments",
        indexes = {
                @Index(
                        name = "idx_teacher_assignment_class",
                        columnList = "class_id"
                ),
                @Index(
                        name = "idx_teacher_assignment_teacher",
                        columnList = "teacher_id"
                )
        }
)
public class TeacherAssignmentJpaEntity {

    @EmbeddedId
    private TeacherAssignmentId id;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "class_id",
                    nullable = false
            )
    )
    private ClassId classId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "teacher_id",
                    nullable = false
            )
    )
    private StaffId teacherId;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(
                    name = "description",
                    nullable = false,
                    length = 200
            )
    )
    private Description description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "teacher_assignment_days",
            joinColumns = @JoinColumn(
                    name = "assignment_id"
            )
    )
    @Enumerated(EnumType.STRING)
    @Column(
            name = "day_of_week",
            nullable = false
    )
    private Set<DayOfWeek> daysOfWeek = new HashSet<>();

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false
    )
    private TeacherAssignmentStatus status;

    public TeacherAssignmentJpaEntity() {
    }

    public TeacherAssignmentId getId() {
        return id;
    }

    public void setId(TeacherAssignmentId id) {
        this.id = id;
    }

    public ClassId getClassId() {
        return classId;
    }

    public void setClassId(ClassId classId) {
        this.classId = classId;
    }

    public StaffId getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(StaffId teacherId) {
        this.teacherId = teacherId;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Set<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(Set<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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

    public TeacherAssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(TeacherAssignmentStatus status) {
        this.status = status;
    }
}
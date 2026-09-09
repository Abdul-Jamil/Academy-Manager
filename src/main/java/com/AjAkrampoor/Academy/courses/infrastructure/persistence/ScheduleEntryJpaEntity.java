package com.AjAkrampoor.Academy.courses.infrastructure.persistence;

import com.AjAkrampoor.Academy.courses.domain.model.*;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(
        name = "schedule_entries",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_schedule_day_period",
                        columnNames = {
                                "schedule_id",
                                "day_of_week",
                                "period_number"
                        }
                )
        }
)
public class ScheduleEntryJpaEntity {

    @EmbeddedId
    private ScheduleEntryId id;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "schedule_id", nullable = false)
    )
    private ClassScheduleId scheduleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "period_number", nullable = false)
    private int periodNumber;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "course_id", nullable = false)
    )
    private CourseId courseId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "teacher_id", nullable = false)
    )
    private StaffId teacherId;

    public ScheduleEntryJpaEntity() {
    }

    public ScheduleEntryId getId() {
        return id;
    }

    public void setId(ScheduleEntryId id) {
        this.id = id;
    }

    public ClassScheduleId getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(ClassScheduleId scheduleId) {
        this.scheduleId = scheduleId;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(int periodNumber) {
        this.periodNumber = periodNumber;
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

    public CourseId getCourseId() {
        return courseId;
    }

    public void setCourseId(CourseId courseId) {
        this.courseId = courseId;
    }

    public StaffId getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(StaffId teacherId) {
        this.teacherId = teacherId;
    }
}

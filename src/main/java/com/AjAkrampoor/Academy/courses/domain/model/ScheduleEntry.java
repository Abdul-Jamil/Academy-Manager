package com.AjAkrampoor.Academy.courses.domain.model;

import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class ScheduleEntry {

    private ScheduleEntryId id;
    private ClassScheduleId scheduleId;
    private DayOfWeek dayOfWeek;
    private int periodNumber;
    private LocalTime startTime;
    private LocalTime endTime;
    private CourseId courseId;
    private StaffId teacherId;

    public ScheduleEntry(
            ScheduleEntryId id,
            ClassScheduleId scheduleId,
            DayOfWeek dayOfWeek,
            int periodNumber,
            LocalTime startTime,
            LocalTime endTime,
            CourseId courseId,
            StaffId teacherId
    ) {
        this.id = Objects.requireNonNull(id, "Schedule entry ID must not be null");
        this.scheduleId = Objects.requireNonNull(scheduleId, "Schedule ID must not be null");
        this.dayOfWeek = Objects.requireNonNull(dayOfWeek, "Day of week must not be null");
        this.courseId = Objects.requireNonNull(courseId, "Course ID must not be null");
        this.teacherId = Objects.requireNonNull(teacherId, "Teacher ID must not be null");

        validatePeriod(periodNumber);
        validateTimeRange(startTime, endTime);

        this.periodNumber = periodNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void update(
            DayOfWeek dayOfWeek,
            int periodNumber,
            LocalTime startTime,
            LocalTime endTime,
            CourseId courseId,
            StaffId teacherId
    ) {
        this.dayOfWeek = Objects.requireNonNull(dayOfWeek, "Day of week must not be null");
        this.courseId = Objects.requireNonNull(courseId, "Course ID must not be null");
        this.teacherId = Objects.requireNonNull(teacherId, "Teacher ID must not be null");

        validatePeriod(periodNumber);
        validateTimeRange(startTime, endTime);

        this.periodNumber = periodNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validatePeriod(int periodNumber) {
        if (periodNumber < 1) {
            throw new IllegalArgumentException("Period number must be greater than or equal to 1");
        }
    }

    private void validateTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime == null && endTime == null) {
            return;
        }

        if (startTime == null) {
            throw new IllegalArgumentException("Start time is required when end time is provided");
        }

        if (endTime == null) {
            throw new IllegalArgumentException("End time is required when start time is provided");
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }

    public ScheduleEntryId getId() {
        return id;
    }

    public ClassScheduleId getScheduleId() {
        return scheduleId;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPeriodNumber() {
        return periodNumber;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public CourseId getCourseId() {
        return courseId;
    }

    public StaffId getTeacherId() {
        return teacherId;
    }
}

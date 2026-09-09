package com.AjAkrampoor.Academy.courses.application.dto;

import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntry;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ScheduleEntryResponse {

    private String id;
    private String scheduleId;
    private DayOfWeek dayOfWeek;
    private int periodNumber;
    private LocalTime startTime;
    private LocalTime endTime;

    private String courseId;
    private String courseName;

    private String teacherId;
    private String teacherName;

    public ScheduleEntryResponse(
            String id,
            String scheduleId,
            DayOfWeek dayOfWeek,
            int periodNumber,
            LocalTime startTime,
            LocalTime endTime,
            String courseId,
            String courseName,
            String teacherId,
            String teacherName
    ) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.dayOfWeek = dayOfWeek;
        this.periodNumber = periodNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public String getId() {
        return id;
    }

    public String getScheduleId() {
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

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }
}

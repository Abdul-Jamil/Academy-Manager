package com.AjAkrampoor.Academy.attendance.application.dto;

import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceStatus;

import java.time.LocalDateTime;

public class AttendanceResponse {

    private String id;
    private String studentName;
    private String className;
    private LocalDateTime attendanceTime;
    private AttendanceStatus status;
    private String description;

    public AttendanceResponse(
            String id,
            String studentName,
            String className,
            LocalDateTime attendanceTime,
            AttendanceStatus status,
            String description
    ) {
        this.id = id;
        this.studentName = studentName;
        this.className = className;
        this.attendanceTime = attendanceTime;
        this.status = status;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getClassName() {
        return className;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}

package com.AjAkrampoor.Academy.attendance.application.dto;

import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UpdateAttendanceRequest {

    @NotNull(message = "Attendance time cannot be null")
    private LocalDateTime attendanceTime;

    @NotNull(message = "Attendance status cannot be null")
    private AttendanceStatus status;

    @Size(
            min = 3,
            max = 200,
            message = "Description must be between 3 and 200 characters"
    )
    private String description;

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.AjAkrampoor.Academy.attendance.domain.model;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Attendance {

    private final AttendanceId id;
    private final StudentId studentId;
    private final ClassId classId;
    private LocalDateTime attendanceTime;
    private AttendanceStatus status;
    private Description description;

    public Attendance(AttendanceId id, StudentId studentId, ClassId classId, LocalDateTime attendanceTime, AttendanceStatus status, Description description) {
        this.id = Objects.requireNonNull(id, "Attendance ID cannot be null");
        this.studentId = Objects.requireNonNull(studentId, "Student ID cannot be null");
        this.classId = Objects.requireNonNull(classId, "Class ID cannot be null");
        this.attendanceTime = Objects.requireNonNull(attendanceTime, "Attendance time cannot be null");
        this.status = Objects.requireNonNull(status, "Attendance status cannot be null");
        this.description = description;
    }

    public void update(LocalDateTime attendanceTime, AttendanceStatus status, Description description) {
        this.attendanceTime = Objects.requireNonNull(attendanceTime, "Attendance time cannot be null");
        this.status = Objects.requireNonNull(status, "Attendance status cannot be null");
        this.description = description;
    }

    public AttendanceId getId() {
        return id;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public ClassId getClassId() {
        return classId;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public Description getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attendance that = (Attendance) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.AjAkrampoor.Academy.attendance.infrastructure.persistence;

import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceId;
import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceStatus;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "attendances"
)
public class AttendanceJpaEntity {

    @EmbeddedId
    private AttendanceId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "student_id", nullable = false))
    private StudentId studentId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "class_id", nullable = false))
    private ClassId classId;

    @Column(name = "attendance_time", nullable = false)
    private LocalDateTime attendanceTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttendanceStatus status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private Description description;

    public AttendanceJpaEntity() {
    }

    public AttendanceId getId() {
        return id;
    }

    public void setId(AttendanceId id) {
        this.id = id;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public void setStudentId(StudentId studentId) {
        this.studentId = studentId;
    }

    public ClassId getClassId() {
        return classId;
    }

    public void setClassId(ClassId classId) {
        this.classId = classId;
    }

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

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}

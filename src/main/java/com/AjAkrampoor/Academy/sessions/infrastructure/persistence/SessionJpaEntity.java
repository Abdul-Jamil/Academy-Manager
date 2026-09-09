package com.AjAkrampoor.Academy.sessions.infrastructure.persistence;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntryId;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionDate;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionId;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionStatus;
import com.AjAkrampoor.Academy.sessions.domain.model.TimeSlot;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

@Entity
@Table(
        name = "sessions",
        uniqueConstraints = {@UniqueConstraint(name = "uk_session_schedule_entry_date", columnNames = {"schedule_entry_id", "session_date"})}
)
public class SessionJpaEntity {

    @EmbeddedId
    private SessionId sessionId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "schedule_entry_id", nullable = false))
    private ScheduleEntryId scheduleEntryId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "class_id", nullable = false))
    private ClassId classId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "teacher_id", nullable = false))
    private UserId teacherId;

    @Embedded
    @AttributeOverride(name = "date", column = @Column(name = "session_date", nullable = false))
    private SessionDate sessionDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startTime", column = @Column(name = "start_time", nullable = false)),
            @AttributeOverride(name = "endTime", column = @Column(name = "end_time", nullable = false))
    })
    private TimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SessionStatus status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private Description description;

    public SessionJpaEntity() {
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public ScheduleEntryId getScheduleEntryId() {
        return scheduleEntryId;
    }

    public void setScheduleEntryId(ScheduleEntryId scheduleEntryId) {
        this.scheduleEntryId = scheduleEntryId;
    }

    public ClassId getClassId() {
        return classId;
    }

    public void setClassId(ClassId classId) {
        this.classId = classId;
    }

    public UserId getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UserId teacherId) {
        this.teacherId = teacherId;
    }

    public SessionDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(SessionDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}

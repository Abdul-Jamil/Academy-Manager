package com.AjAkrampoor.Academy.sessions.domain.model;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntryId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.util.Objects;

public class Session {

    private SessionId sessionId;
    private ScheduleEntryId scheduleEntryId;
    private ClassId classId;
    private UserId teacherId;
    private SessionDate sessionDate;
    private TimeSlot timeSlot;
    private SessionStatus status;
    private Description description;

    public Session(SessionId sessionId, ScheduleEntryId scheduleEntryId, ClassId classId, UserId teacherId, SessionDate sessionDate, TimeSlot timeSlot, SessionStatus status, Description description) {
        this.sessionId = Objects.requireNonNull(sessionId, "Session ID must not be null");
        this.scheduleEntryId = Objects.requireNonNull(scheduleEntryId, "Schedule entry ID must not be null");
        this.classId = Objects.requireNonNull(classId, "Class ID must not be null");
        this.teacherId = Objects.requireNonNull(teacherId, "Teacher ID must not be null");
        this.sessionDate = Objects.requireNonNull(sessionDate, "Session date must not be null");
        this.timeSlot = Objects.requireNonNull(timeSlot, "Time slot must not be null");
        this.status = Objects.requireNonNull(status, "Session status must not be null");
        this.description = description;
    }

    public void cancel() {
        if (this.status == SessionStatus.CANCELLED) {
            throw new IllegalStateException("Session is already cancelled");
        }
        this.status = SessionStatus.CANCELLED;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public ScheduleEntryId getScheduleEntryId() {
        return scheduleEntryId;
    }

    public ClassId getClassId() {
        return classId;
    }

    public UserId getTeacherId() {
        return teacherId;
    }

    public SessionDate getSessionDate() {
        return sessionDate;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public Description getDescription() {
        return description;
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        return Objects.equals(sessionId, session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}

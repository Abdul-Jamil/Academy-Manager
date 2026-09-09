package com.AjAkrampoor.Academy.sessions.domain.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntryId;
import com.AjAkrampoor.Academy.sessions.domain.model.Session;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionDate;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionId;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionStatus;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SessionRepository {

    boolean existsById(SessionId sessionId);

    Optional<Session> findById(SessionId sessionId);

    Session save(Session session);

    boolean existsByScheduleEntryIdAndSessionDateAndStatus(
            ScheduleEntryId scheduleEntryId,
            SessionDate sessionDate,
            SessionStatus status
    );

    List<Session> findByClassIdAndSessionDateBetween(
            ClassId classId,
            LocalDate from,
            LocalDate to
    );

    List<Session> findByTeacherIdAndSessionDateBetween(
            UserId teacherId,
            LocalDate from,
            LocalDate to,
            SessionStatus status
    );
}

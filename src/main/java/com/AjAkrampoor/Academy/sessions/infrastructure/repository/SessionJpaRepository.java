package com.AjAkrampoor.Academy.sessions.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntryId;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionDate;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionId;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionStatus;
import com.AjAkrampoor.Academy.sessions.infrastructure.persistence.SessionJpaEntity;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessionJpaRepository
        extends JpaRepository<SessionJpaEntity, SessionId> {

    boolean existsByScheduleEntryIdAndSessionDateAndStatus(
            ScheduleEntryId scheduleEntryId,
            SessionDate sessionDate,
            SessionStatus status
    );

    List<SessionJpaEntity> findByClassIdAndSessionDateDateBetween(
            ClassId classId,
            LocalDate from,
            LocalDate to
    );

    List<SessionJpaEntity> findByTeacherIdAndSessionDateDateBetweenAndStatus(
            UserId teacherId,
            LocalDate from,
            LocalDate to,
            SessionStatus status
    );
}

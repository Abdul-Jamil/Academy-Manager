package com.AjAkrampoor.Academy.sessions.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntryId;
import com.AjAkrampoor.Academy.sessions.domain.model.Session;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionDate;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionId;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionStatus;
import com.AjAkrampoor.Academy.sessions.domain.repository.SessionRepository;
import com.AjAkrampoor.Academy.sessions.infrastructure.persistence.SessionJpaEntity;
import com.AjAkrampoor.Academy.sessions.presentation.mapper.SessionMapper;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private final SessionJpaRepository repository;

    public SessionRepositoryImpl(
            SessionJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(SessionId sessionId) {
        return repository.existsById(sessionId);
    }

    @Override
    public Optional<Session> findById(SessionId sessionId) {
        return repository.findById(sessionId)
                .map(SessionMapper::toDomain);
    }

    @Override
    public Session save(Session session) {

        SessionJpaEntity saved =
                repository.save(
                        SessionMapper.toEntity(session)
                );

        return SessionMapper.toDomain(saved);
    }

    @Override
    public boolean existsByScheduleEntryIdAndSessionDateAndStatus(
            ScheduleEntryId scheduleEntryId,
            SessionDate sessionDate,
            SessionStatus status
    ) {
        return repository.existsByScheduleEntryIdAndSessionDateAndStatus(
                scheduleEntryId,
                sessionDate,
                status
        );
    }

    @Override
    public List<Session> findByClassIdAndSessionDateBetween(
            ClassId classId,
            LocalDate from,
            LocalDate to
    ) {
        return repository
                .findByClassIdAndSessionDateDateBetween(
                        classId,
                        from,
                        to
                )
                .stream()
                .map(SessionMapper::toDomain)
                .toList();
    }

    @Override
    public List<Session> findByTeacherIdAndSessionDateBetween(
            UserId teacherId,
            LocalDate from,
            LocalDate to,
            SessionStatus status
    ) {
        return repository
                .findByTeacherIdAndSessionDateDateBetweenAndStatus(
                        teacherId,
                        from,
                        to,
                        status
                )
                .stream()
                .map(SessionMapper::toDomain)
                .toList();
    }
}

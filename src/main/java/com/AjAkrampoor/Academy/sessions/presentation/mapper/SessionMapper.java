package com.AjAkrampoor.Academy.sessions.presentation.mapper;

import com.AjAkrampoor.Academy.sessions.domain.model.Session;
import com.AjAkrampoor.Academy.sessions.infrastructure.persistence.SessionJpaEntity;

public final class SessionMapper {

    private SessionMapper() {
    }

    public static Session toDomain(SessionJpaEntity entity) {

        return new Session(
                entity.getSessionId(),
                entity.getScheduleEntryId(),
                entity.getClassId(),
                entity.getTeacherId(),
                entity.getSessionDate(),
                entity.getTimeSlot(),
                entity.getStatus(),
                entity.getDescription()
        );
    }

    public static SessionJpaEntity toEntity(Session domain) {

        SessionJpaEntity entity = new SessionJpaEntity();

        entity.setSessionId(domain.getSessionId());
        entity.setScheduleEntryId(domain.getScheduleEntryId());
        entity.setClassId(domain.getClassId());
        entity.setTeacherId(domain.getTeacherId());
        entity.setSessionDate(domain.getSessionDate());
        entity.setTimeSlot(domain.getTimeSlot());
        entity.setStatus(domain.getStatus());
        entity.setDescription(domain.getDescription());

        return entity;
    }
}

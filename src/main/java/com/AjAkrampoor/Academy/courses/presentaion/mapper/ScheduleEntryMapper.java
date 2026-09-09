package com.AjAkrampoor.Academy.courses.presentaion.mapper;

import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntry;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.ScheduleEntryJpaEntity;

public final class ScheduleEntryMapper {

    private ScheduleEntryMapper() {
    }

    public static ScheduleEntry toDomain(ScheduleEntryJpaEntity entity) {
        return new ScheduleEntry(
                entity.getId(),
                entity.getScheduleId(),
                entity.getDayOfWeek(),
                entity.getPeriodNumber(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getCourseId(),
                entity.getTeacherId()
        );
    }

    public static ScheduleEntryJpaEntity toEntity(ScheduleEntry domain) {
        ScheduleEntryJpaEntity entity = new ScheduleEntryJpaEntity();

        entity.setId(domain.getId());
        entity.setScheduleId(domain.getScheduleId());
        entity.setDayOfWeek(domain.getDayOfWeek());
        entity.setPeriodNumber(domain.getPeriodNumber());
        entity.setStartTime(domain.getStartTime());
        entity.setEndTime(domain.getEndTime());
        entity.setCourseId(domain.getCourseId());
        entity.setTeacherId(domain.getTeacherId());

        return entity;
    }
}

package com.AjAkrampoor.Academy.courses.presentaion.mapper;

import com.AjAkrampoor.Academy.courses.domain.model.ClassSchedule;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.ClassScheduleJpaEntity;

public final class ClassScheduleMapper {

    private ClassScheduleMapper() {
    }

    public static ClassSchedule toDomain(ClassScheduleJpaEntity entity) {
        return new ClassSchedule(
                entity.getId(),
                entity.getClassId(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getStatus()
        );
    }

    public static ClassScheduleJpaEntity toEntity(ClassSchedule domain) {
        ClassScheduleJpaEntity entity = new ClassScheduleJpaEntity();

        entity.setId(domain.getId());
        entity.setClassId(domain.getClassId());
        entity.setEffectiveFrom(domain.getEffectiveFrom());
        entity.setEffectiveTo(domain.getEffectiveTo());
        entity.setStatus(domain.getStatus());

        return entity;
    }
}

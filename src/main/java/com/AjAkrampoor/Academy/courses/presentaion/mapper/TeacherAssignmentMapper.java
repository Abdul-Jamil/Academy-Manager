package com.AjAkrampoor.Academy.courses.presentaion.mapper;

import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.TeacherAssignmentJpaEntity;

public final class TeacherAssignmentMapper {

    private TeacherAssignmentMapper() {
    }

    public static TeacherAssignment toDomain(
            TeacherAssignmentJpaEntity entity
    ) {
        return new TeacherAssignment(
                entity.getId(),
                entity.getClassId(),
                entity.getTeacherId(),
                entity.getDescription(),
                entity.getDaysOfWeek(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getStatus()
        );
    }

    public static TeacherAssignmentJpaEntity toEntity(
            TeacherAssignment domain
    ) {
        TeacherAssignmentJpaEntity entity =
                new TeacherAssignmentJpaEntity();

        entity.setId(domain.getId());
        entity.setClassId(domain.getClassId());
        entity.setTeacherId(domain.getTeacherId());
        entity.setDescription(domain.getDescription());
        entity.setDaysOfWeek(domain.getDaysOfWeek());
        entity.setStartTime(domain.getStartTime());
        entity.setEndTime(domain.getEndTime());
        entity.setEffectiveFrom(domain.getEffectiveFrom());
        entity.setEffectiveTo(domain.getEffectiveTo());
        entity.setStatus(domain.getStatus());

        return entity;
    }
}
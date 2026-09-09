package com.AjAkrampoor.Academy.courses.presentaion.mapper;

import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseClassJpaEntity;

public final class CourseClassMapper {

    private CourseClassMapper() {
    }

    public static CourseClass toDomain(CourseClassJpaEntity entity) {
        return new CourseClass(
                entity.getId(),
                entity.getDuration(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getDescription(),
                entity.getFee(),
                entity.getClassType(),
                entity.getClassStatus(),
                entity.getBranchId()
        );
    }

    public static CourseClassJpaEntity toEntity(CourseClass domain) {
        CourseClassJpaEntity entity = new CourseClassJpaEntity();

        entity.setId(domain.getId());
        entity.setDuration(domain.getDuration());
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setDescription(domain.getDescription());
        entity.setFee(domain.getFee());
        entity.setClassType(domain.getClassType());
        entity.setClassStatus(domain.getClassStatus());
        entity.setBranchId(domain.getBranchId());

        return entity;
    }
}

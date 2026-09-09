package com.AjAkrampoor.Academy.courses.presentaion.mapper;

import com.AjAkrampoor.Academy.courses.domain.model.Course;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseJpaEntity;

import java.util.List;

public final class CourseMapper {

    private CourseMapper() {
    }

    public static Course toDomain(CourseJpaEntity entity) {

        return new Course(
                entity.getCourseId(),
                entity.getDescription(),
                entity.getCourseName(),
                entity.getStatus()
        );
    }

    public static CourseJpaEntity toEntity(Course domain) {

        CourseJpaEntity entity = new CourseJpaEntity();

        entity.setCourseId(domain.getCourseId());
        entity.setDescription(domain.getDescription());
        entity.setCourseName(domain.getCourseName());
        entity.setStatus(domain.getCourseStatus());

        return entity;
    }

    public static List<Course> toDomainList(
            List<CourseJpaEntity> entities
    ) {
        return entities.stream()
                .map(CourseMapper::toDomain)
                .toList();
    }

    public static List<CourseJpaEntity> toEntityList(
            List<Course> domains
    ) {
        return domains.stream()
                .map(CourseMapper::toEntity)
                .toList();
    }
}

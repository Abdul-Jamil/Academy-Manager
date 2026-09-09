package com.AjAkrampoor.Academy.enrollments.presentation.mapper;

import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.infrastructure.persistence.EnrollmentJpaEntity;

import java.util.List;

public class EnrollmentMapper {

    public static Enrollment toDomain(EnrollmentJpaEntity entity) {
        return new Enrollment
                (
                        entity.getId(),
                        entity.getDescription(),
                        entity.getStudentId(),
                        entity.getClassId(),
                        entity.getEnrolledAt(),
                        entity.getEnrolledBy(),
                        entity.getEnrollmentStatus(),
                        entity.getTransferredTo(),
                        entity.getTransferredAt(),
                        entity.getCancelledBy(),
                        entity.getCancelledAt(),
                        entity.getTransferredBy()
                );
    }

    public static EnrollmentJpaEntity toEntity(Enrollment enrollment) {
        EnrollmentJpaEntity entity = new EnrollmentJpaEntity();
        entity.setId(enrollment.getEnrollmentId());
        entity.setDescription(enrollment.getDescription());
        entity.setStudentId(enrollment.getStudentId());
        entity.setClassId(enrollment.getClassId());
        entity.setEnrolledAt(enrollment.getEnrolledAt());
        entity.setEnrolledBy(enrollment.getEnrolledBy());
        entity.setEnrollmentStatus(enrollment.getEnrollmentStatus());
        entity.setTransferredTo(enrollment.getTransferredTo());
        entity.setTransferredAt(enrollment.getTransferredAt());
        entity.setCancelledBy(enrollment.getCancelledBy());
        entity.setCancelledAt(enrollment.getCancelledAt());
        entity.setTransferredBy(enrollment.getTransferredBy());

        return entity;
    }

    public static List<Enrollment> toDomain(List<EnrollmentJpaEntity> entities) {
        return entities.stream()
                .map(EnrollmentMapper::toDomain)
                .toList();
    }

    public static List<EnrollmentJpaEntity> toEntities(List<Enrollment> enrollments) {
        return enrollments.stream()
                .map(EnrollmentMapper::toEntity)
                .toList();
    }
}

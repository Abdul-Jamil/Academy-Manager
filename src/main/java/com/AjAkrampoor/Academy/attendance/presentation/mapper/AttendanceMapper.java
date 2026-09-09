package com.AjAkrampoor.Academy.attendance.presentation.mapper;

import com.AjAkrampoor.Academy.attendance.domain.model.Attendance;
import com.AjAkrampoor.Academy.attendance.infrastructure.persistence.AttendanceJpaEntity;

public final class AttendanceMapper {

    private AttendanceMapper() {
    }

    public static Attendance toDomain(AttendanceJpaEntity entity) {
        return new Attendance(
                entity.getId(),
                entity.getStudentId(),
                entity.getClassId(),
                entity.getAttendanceTime(),
                entity.getStatus(),
                entity.getDescription()
        );
    }

    public static AttendanceJpaEntity toEntity(Attendance domain) {
        AttendanceJpaEntity entity = new AttendanceJpaEntity();

        entity.setId(domain.getId());
        entity.setStudentId(domain.getStudentId());
        entity.setClassId(domain.getClassId());
        entity.setAttendanceTime(domain.getAttendanceTime());
        entity.setStatus(domain.getStatus());
        entity.setDescription(domain.getDescription());

        return entity;
    }
}

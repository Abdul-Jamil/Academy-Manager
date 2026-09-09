package com.AjAkrampoor.Academy.attendance.infrastructure.repository;

import com.AjAkrampoor.Academy.attendance.application.dto.AttendanceFilter;
import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceStatus;
import com.AjAkrampoor.Academy.attendance.infrastructure.persistence.AttendanceJpaEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class AttendanceSpecifications {

    private AttendanceSpecifications() {
    }

    public static List<Specification<AttendanceJpaEntity>> getSpecifications(
            AttendanceFilter filter
    ) {
        List<Specification<AttendanceJpaEntity>> specifications =
                new ArrayList<>();

        if (filter == null) {
            return specifications;
        }

        if (filter.getStudentId() != null && !filter.getStudentId().isBlank()) {
            specifications.add(hasStudentId(filter.getStudentId()));
        }

        if (filter.getClassId() != null && !filter.getClassId().isBlank()) {

            specifications.add(hasClassId(filter.getClassId()));
        }

        if (filter.getDateFrom() != null || filter.getDateTo() != null) {

            specifications.add(attendanceDateRange(filter.getDateFrom(), filter.getDateTo()));
        }

        if (filter.getDescription() != null && !filter.getDescription().isBlank()) {

            specifications.add(hasDescriptionContaining(filter.getDescription()));
        }

        if (filter.getStatus() != null) {
            specifications.add(hasStatus(filter.getStatus()));
        }

        return specifications;
    }

    public static Specification<AttendanceJpaEntity> hasStudentId(String studentId) {
        return (root, query, cb) ->
                cb.equal(root.get("studentId").get("id"), studentId.trim());
    }

    public static Specification<AttendanceJpaEntity> hasClassId(String classId) {
        return (root, query, cb) ->
                cb.equal(root.get("classId").get("id"), classId.trim());
    }

    public static Specification<AttendanceJpaEntity> attendanceDateRange(LocalDate from, LocalDate to) {
        return (root, query, cb) -> {

            if (from != null && to != null) {
                LocalDateTime fromDateTime = from.atStartOfDay();

                LocalDateTime toDateTime = to.plusDays(1).atStartOfDay();

                return cb.and(cb.greaterThanOrEqualTo(root.get("attendanceTime"), fromDateTime),
                        cb.lessThan(root.get("attendanceTime"), toDateTime));
            }

            if (from != null) {
                return cb.greaterThanOrEqualTo(root.get("attendanceTime"), from.atStartOfDay());
            }

            return cb.lessThan(root.get("attendanceTime"), to.plusDays(1).atStartOfDay());
        };
    }

    public static Specification<AttendanceJpaEntity> hasDescriptionContaining(String description) {
        return (root, query, cb) -> {

            String pattern = "%" + description.trim().toLowerCase() + "%";

            return cb.like(cb.lower(root.get("description").get("value")), pattern);
        };
    }

    public static Specification<AttendanceJpaEntity> hasStatus(AttendanceStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }
}

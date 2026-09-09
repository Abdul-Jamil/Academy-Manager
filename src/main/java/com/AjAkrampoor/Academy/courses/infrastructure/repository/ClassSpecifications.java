package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.application.dto.ClassFilter;
import com.AjAkrampoor.Academy.courses.domain.model.ClassStatus;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseClassJpaEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class ClassSpecifications {

    private ClassSpecifications() {
    }

    public static List<Specification<CourseClassJpaEntity>> getSpecifications(
            ClassFilter filter
    ) {
        List<Specification<CourseClassJpaEntity>> specifications =
                new ArrayList<>();

        if (filter == null) {
            return specifications;
        }

        if (filter.getDescription() != null &&
                !filter.getDescription().isBlank()) {

            specifications.add(
                    hasDescriptionContaining(filter.getDescription())
            );
        }

        if (filter.getBranchId() != null) {
            specifications.add(
                    hasBranchId(filter.getBranchId().toString())
            );
        }

        if (filter.getStartDateFrom() != null ||
                filter.getStartDateTo() != null) {

            specifications.add(
                    startDateRange(
                            filter.getStartDateFrom(),
                            filter.getStartDateTo()
                    )
            );
        }

        if (filter.getEndDateFrom() != null ||
                filter.getEndDateTo() != null) {

            specifications.add(
                    endDateRange(
                            filter.getEndDateFrom(),
                            filter.getEndDateTo()
                    )
            );
        }

        if (filter.getFeeMin() != null ||
                filter.getFeeMax() != null) {

            specifications.add(
                    feeRange(
                            filter.getFeeMin(),
                            filter.getFeeMax()
                    )
            );
        }

        if (filter.getStatus() != null) {
            specifications.add(
                    hasStatus(filter.getStatus())
            );
        }

        if (filter.isOverdue()) {
            specifications.add(overdue());
        }

        return specifications;
    }

    public static Specification<CourseClassJpaEntity>
    hasDescriptionContaining(String description) {

        return (root, query, cb) -> {

            String pattern =
                    "%" + description.trim().toLowerCase() + "%";

            return cb.like(
                    cb.lower(
                            root.get("description").get("value")
                    ),
                    pattern
            );
        };
    }

    public static Specification<CourseClassJpaEntity>
    hasBranchId(String branchId) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("branchId").get("id"),
                        branchId
                );
    }

    public static Specification<CourseClassJpaEntity>
    hasStatus(ClassStatus status) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("classStatus"),
                        status
                );
    }

    public static Specification<CourseClassJpaEntity>
    startDateRange(
            LocalDate from,
            LocalDate to
    ) {

        return (root, query, cb) -> {

            if (from != null && to != null) {
                return cb.between(
                        root.get("startDate"),
                        from,
                        to
                );
            }

            if (from != null) {
                return cb.greaterThanOrEqualTo(
                        root.get("startDate"),
                        from
                );
            }

            return cb.lessThanOrEqualTo(
                    root.get("startDate"),
                    to
            );
        };
    }

    public static Specification<CourseClassJpaEntity>
    endDateRange(
            LocalDate from,
            LocalDate to
    ) {

        return (root, query, cb) -> {

            if (from != null && to != null) {
                return cb.between(
                        root.get("endDate"),
                        from,
                        to
                );
            }

            if (from != null) {
                return cb.greaterThanOrEqualTo(
                        root.get("endDate"),
                        from
                );
            }

            return cb.lessThanOrEqualTo(
                    root.get("endDate"),
                    to
            );
        };
    }

    public static Specification<CourseClassJpaEntity>
    feeRange(
            BigDecimal min,
            BigDecimal max
    ) {

        return (root, query, cb) -> {

            if (min != null && max != null) {
                return cb.between(
                        root.get("fee").get("amount"),
                        min,
                        max
                );
            }

            if (min != null) {
                return cb.greaterThanOrEqualTo(
                        root.get("fee").get("amount"),
                        min
                );
            }

            return cb.lessThanOrEqualTo(
                    root.get("fee").get("amount"),
                    max
            );
        };
    }

    public static Specification<CourseClassJpaEntity> overdue() {

        return (root, query, cb) -> {

            return cb.and(

                    cb.not(
                            root.get("classStatus").in(
                                    ClassStatus.CANCELED,
                                    ClassStatus.FINISHED
                            )
                    ),

                    cb.isNotNull(
                            root.get("startDate")
                    ),

                    cb.isNull(
                            root.get("endDate")
                    ),

                    cb.lessThan(
                            cb.function(
                                    "ADDDATE",
                                    java.sql.Date.class,
                                    root.get("startDate"),
                                    root.get("duration").get("duration")
                            ),
                            cb.currentDate()
                    )
            );
        };
    }
}

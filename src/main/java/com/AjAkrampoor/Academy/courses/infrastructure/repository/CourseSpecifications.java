package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.application.dto.CourseFilter;
import com.AjAkrampoor.Academy.courses.domain.model.CourseStatus;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseJpaEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class CourseSpecifications {

    private CourseSpecifications() {
    }

    public static List<Specification<CourseJpaEntity>> getSpecifications(
            CourseFilter filter
    ) {
        List<Specification<CourseJpaEntity>> specifications =
                new ArrayList<>();

        if (filter == null) {
            return specifications;
        }

        if (filter.getName() != null &&
                !filter.getName().isBlank()) {

            specifications.add(
                    hasNameContaining(filter.getName())
            );
        }

        if (filter.getStatus() != null) {
            specifications.add(
                    hasStatus(filter.getStatus())
            );
        }

        return specifications;
    }

    public static Specification<CourseJpaEntity>
    hasNameContaining(String name) {

        return (root, query, cb) -> {

            String pattern =
                    "%" + name.trim().toLowerCase() + "%";

            return cb.like(
                    cb.lower(
                            root.get("courseName").get("value")
                    ),
                    pattern
            );
        };
    }

    public static Specification<CourseJpaEntity>
    hasStatus(CourseStatus status) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("status"),
                        status
                );
    }
}

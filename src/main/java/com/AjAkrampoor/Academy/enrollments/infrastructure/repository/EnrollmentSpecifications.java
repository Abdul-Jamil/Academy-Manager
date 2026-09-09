package com.AjAkrampoor.Academy.enrollments.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseClassJpaEntity;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentFilter;
import com.AjAkrampoor.Academy.enrollments.infrastructure.persistence.EnrollmentJpaEntity;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentSpecifications {

    public static Specification<EnrollmentJpaEntity> filter(EnrollmentFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // ---------- ID ----------
            if (filter.getEnrollmentId() != null) {
                predicates.add(
                        cb.equal(root.get("enrollmentId"), filter.getEnrollmentId())
                );
            }

            // ---------- Description ----------
            if (filter.getDescription() != null) {
                predicates.add(
                        cb.equal(root.get("description"), filter.getDescription())
                );
            }

            if (filter.getDescriptionText() != null && !filter.getDescriptionText().isBlank()) {
                predicates.add(
                        cb.like(
                                root.get("description").get("value"),
                                "%" + filter.getDescriptionText() + "%"
                        )
                );
            }

            // ---------- Branch Filter ----------
            if (filter.getBranchId() != null) {
                assert query != null;
                Subquery<Integer> subquery = query.subquery(Integer.class);
                Root<CourseClassJpaEntity> classRoot = subquery.from(CourseClassJpaEntity.class);

                subquery.select(cb.literal(1))
                        .where(
                                cb.and(
                                        cb.equal(classRoot.get("id"), root.get("classId")),
                                        cb.equal(classRoot.get("branchId"), filter.getBranchId())
                                )
                        );

                predicates.add(cb.exists(subquery));
            }

            // ---------- Student ----------
            if (filter.getStudentId() != null) {
                predicates.add(
                        cb.equal(root.get("studentId"), filter.getStudentId())
                );
            }

            // ---------- Class ----------
            if (filter.getClassId() != null) {
                predicates.add(
                        cb.equal(root.get("classId"), filter.getClassId())
                );
            }

            // ---------- Enrolled ----------
            if (filter.getEnrolledAfter() != null && filter.getEnrolledBefore() != null) {
                predicates.add(
                        cb.between(
                                root.get("enrolledAt"),
                                filter.getEnrolledAfter(),
                                filter.getEnrolledBefore()
                        )
                );
            } else if (filter.getEnrolledAfter() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("enrolledAt"),
                                filter.getEnrolledAfter()
                        )
                );
            } else if (filter.getEnrolledBefore() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("enrolledAt"),
                                filter.getEnrolledBefore()
                        )
                );
            }

            // ---------- Enrolled By ----------
            if (filter.getEnrolledBy() != null) {
                predicates.add(
                        cb.equal(root.get("enrolledBy"), filter.getEnrolledBy())
                );
            }

            // ---------- Status ----------
            if (filter.getStatus() != null) {
                predicates.add(
                        cb.equal(root.get("enrollmentStatus"), filter.getStatus())
                );
            }

            // ---------- Transfer ----------
            if (filter.getTransferredTo() != null) {
                predicates.add(
                        cb.equal(root.get("transferredTo"), filter.getTransferredTo())
                );
            }

            if (filter.getTransferred() != null) {
                predicates.add(
                        filter.getTransferred()
                                ? cb.isNotNull(root.get("transferredTo"))
                                : cb.isNull(root.get("transferredTo"))
                );
            }

            if (filter.getTransferredAfter() != null && filter.getTransferredBefore() != null) {
                predicates.add(
                        cb.between(
                                root.get("transferredAt"),
                                filter.getTransferredAfter(),
                                filter.getTransferredBefore()
                        )
                );
            } else if (filter.getTransferredAfter() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("transferredAt"),
                                filter.getTransferredAfter()
                        )
                );
            } else if (filter.getTransferredBefore() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("transferredAt"),
                                filter.getTransferredBefore()
                        )
                );
            }

            if (filter.getTransferredBy() != null) {
                predicates.add(
                        cb.equal(root.get("transferredBy"), filter.getTransferredBy())
                );
            }

            // ---------- Cancellation ----------
            if (filter.getCancelledBy() != null) {
                predicates.add(
                        cb.equal(root.get("cancelledBy"), filter.getCancelledBy())
                );
            }

            if (filter.getCancelledAfter() != null && filter.getCancelledBefore() != null) {
                predicates.add(
                        cb.between(
                                root.get("cancelledAt"),
                                filter.getCancelledAfter(),
                                filter.getCancelledBefore()
                        )
                );
            } else if (filter.getCancelledAfter() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("cancelledAt"),
                                filter.getCancelledAfter()
                        )
                );
            } else if (filter.getCancelledBefore() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("cancelledAt"),
                                filter.getCancelledBefore()
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

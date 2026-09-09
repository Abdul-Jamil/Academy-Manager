package com.AjAkrampoor.Academy.bills.infrastructure.repository;

import com.AjAkrampoor.Academy.bills.application.dto.BillFilter;
import com.AjAkrampoor.Academy.bills.domain.model.PaymentStatus;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.BillJpaEntity;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.PaymentJpaEntity;
import com.AjAkrampoor.Academy.enrollments.infrastructure.persistence.EnrollmentJpaEntity;
import com.AjAkrampoor.Academy.students.infrastructure.persistence.StudentJpaEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillSpecifications {

    public static List<Specification<BillJpaEntity>> getSpecifications(BillFilter filter) {
        List<Specification<BillJpaEntity>> specs = new ArrayList<>();

        if (filter != null) {
            // ID → BillId.value
            if (filter.getId() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("billId").get("value"), filter.getId())
                );
            }

            // Amount – range or exact → Money.amount
            if (filter.getAmountFrom() != null || filter.getAmountTo() != null) {
                specs.add(amountRange(filter.getAmountFrom(), filter.getAmountTo()));
            } else if (filter.getAmount() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("amount").get("amount"), filter.getAmount())
                );
            }

            // Discount – range or exact → Money.amount
            if (filter.getDiscountFrom() != null || filter.getDiscountTo() != null) {
                specs.add(discountRange(filter.getDiscountFrom(), filter.getDiscountTo()));
            } else if (filter.getDiscount() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("discount").get("amount"), filter.getDiscount())
                );
            }

            // CreatedAt – range or exact
            if (filter.getCreatedAtFrom() != null || filter.getCreatedAtTo() != null) {
                specs.add(createdAtRange(filter.getCreatedAtFrom(), filter.getCreatedAtTo()));
            } else if (filter.getCreatedAt() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("createdAt"), filter.getCreatedAt())
                );
            }

            // Description – contains (case‑insensitive)
            if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
                specs.add((root, query, cb) -> {
                    String pattern = "%" + filter.getDescription().toLowerCase() + "%";
                    return cb.like(
                            cb.lower(root.get("description").get("value")),
                            pattern
                    );
                });
            }

            // Branch ID – exact
            if (filter.getBranchId() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("branchId").get("id"), filter.getBranchId())
                );
            }

            // --- NEW: Bill status filter ---
            if (filter.getStatus() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("status"), filter.getStatus())
                );
            }

            // --- NEW: Filter by remaining amount > 0 ---
            if (filter.getHasRemainingAmount() != null && filter.getHasRemainingAmount()) {
                specs.add(hasRemainingAmount());
            }

            specs.add(byStudentCriteria(
                    filter.getStudentId(),
                    filter.getStudentFirstName(),
                    filter.getStudentLastName()
            ));

            return specs;
        }

        return specs;
    }

    // ---------- Private range helpers ----------
    private static Specification<BillJpaEntity> amountRange(BigDecimal from, BigDecimal to) {
        return (root, query, cb) ->
                buildRange(cb, root.get("amount").get("amount"), from, to);
    }

    private static Specification<BillJpaEntity> discountRange(BigDecimal from, BigDecimal to) {
        return (root, query, cb) ->
                buildRange(cb, root.get("discount").get("amount"), from, to);
    }

    private static Specification<BillJpaEntity> createdAtRange(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) ->
                buildRange(cb, root.get("createdAt"), from, to);
    }

    private static <T extends Comparable<? super T>> Predicate buildRange(
            CriteriaBuilder cb, Path<T> path, T from, T to) {
        if (from != null && to != null) {
            return cb.between(path, from, to);
        } else if (from != null) {
            return cb.greaterThanOrEqualTo(path, from);
        } else {
            return cb.lessThanOrEqualTo(path, to);
        }
    }

    // ---------- New: Filter for unpaid bills (remaining amount > 0) ----------
    private static Specification<BillJpaEntity> hasRemainingAmount() {
        return (root, query, cb) -> {
            // Subquery to sum paid amounts for this bill (only PAID payments)
            assert query != null;
            Subquery<BigDecimal> paidSubquery = query.subquery(BigDecimal.class);
            Root<PaymentJpaEntity> paymentRoot = paidSubquery.from(PaymentJpaEntity.class);
            paidSubquery.select(cb.sum(paymentRoot.get("amount").get("amount")))
                    .where(
                            cb.equal(paymentRoot.get("billId"), root.get("billId")),
                            cb.equal(paymentRoot.get("status"), PaymentStatus.PAID)
                    );

            // Net payable = amount - discount (treat null discount as 0)
            Expression<BigDecimal> netAmount = cb.diff(
                    root.get("amount").get("amount"),
                    cb.coalesce(root.get("discount").get("amount"), BigDecimal.ZERO)
            );

            // Paid amount (coalesce null sum to 0)
            Expression<BigDecimal> paidAmount = cb.coalesce(paidSubquery, BigDecimal.ZERO);

            // Remaining = netAmount - paidAmount
            Expression<BigDecimal> remaining = cb.diff(netAmount, paidAmount);

            // Condition: remaining > 0
            return cb.greaterThan(remaining, BigDecimal.ZERO);
        };
    }

    private static Specification<BillJpaEntity> byStudentCriteria(String studentId,
                                                                  String firstName,
                                                                  String lastName) {
        if ((studentId == null || studentId.isBlank()) &&
                (firstName == null || firstName.isBlank()) &&
                (lastName == null || lastName.isBlank())) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            // Subquery: select enrollment IDs (as string values) from enrollments
            assert query != null;
            Subquery<String> subquery = query.subquery(String.class);
            Root<EnrollmentJpaEntity> enrollmentRoot = subquery.from(EnrollmentJpaEntity.class);
            Root<StudentJpaEntity> studentRoot = subquery.from(StudentJpaEntity.class);

            // Select the enrollment ID value
            subquery.select(enrollmentRoot.get("id").get("value"));

            // (both are embedded IDs)
            var joinCondition = cb.equal(
                    enrollmentRoot.get("studentId").get("id"),
                    studentRoot.get("studentId").get("id")
            );

            // Build student filter predicates
            var studentPredicates = cb.conjunction();
            if (studentId != null && !studentId.isBlank()) {
                studentPredicates = cb.and(
                        studentPredicates,
                        cb.equal(studentRoot.get("studentId").get("id"), studentId)
                );
            }
            if (firstName != null && !firstName.isBlank()) {
                studentPredicates = cb.and(
                        studentPredicates,
                        cb.like(
                                cb.lower(studentRoot.get("studentName").get("firstName")),
                                "%" + firstName.toLowerCase() + "%"
                        )
                );
            }
            if (lastName != null && !lastName.isBlank()) {
                studentPredicates = cb.and(
                        studentPredicates,
                        cb.like(
                                cb.lower(studentRoot.get("studentName").get("lastName")),
                                "%" + lastName.toLowerCase() + "%"
                        )
                );
            }

            // Combine join condition with student filters
            subquery.where(cb.and(joinCondition, studentPredicates));

            // Finally, check if the bill's enrollment ID is in the subquery result
            return cb.in(root.get("enrollmentId").get("value")).value(subquery);
        };

    }
}
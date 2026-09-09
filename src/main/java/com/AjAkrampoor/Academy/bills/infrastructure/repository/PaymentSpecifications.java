package com.AjAkrampoor.Academy.bills.infrastructure.repository;

import com.AjAkrampoor.Academy.bills.application.dto.PaymentFilter;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.BillJpaEntity;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.PaymentJpaEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentSpecifications {

    public static List<Specification<PaymentJpaEntity>> specifications(PaymentFilter filter) {
        List<Specification<PaymentJpaEntity>> specs = new ArrayList<>();

        if (filter != null) {
            // Payment ID → PaymentId.value
            if (filter.getPaymentId() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("paymentId").get("value"), filter.getPaymentId()));
            }

            // Bill ID → BillId.value
            if (filter.getBillId() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("billId").get("value"), filter.getBillId()));
            }

            // Received by → UserId.value
            if (filter.getReceivedBy() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("receivedBy").get("value"), filter.getReceivedBy()));
            }

            // Amount – range or exact → Money.amount
            if (filter.getAmountFrom() != null || filter.getAmountTo() != null) {
                specs.add(amountRange(filter.getAmountFrom(), filter.getAmountTo()));
            } else if (filter.getAmount() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("amount").get("amount"), filter.getAmount()));
            }

            // Description – contains (case‑insensitive)
            if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
                specs.add((root, query, cb) -> {
                    String pattern = "%" + filter.getDescription().toLowerCase() + "%";
                    return cb.like(
                            cb.lower(root.get("description").get("value")),
                            pattern);
                });
            }

            // Date – range or exact
            if (filter.getDateFrom() != null || filter.getDateTo() != null) {
                specs.add(dateRange(filter.getDateFrom(), filter.getDateTo()));
            } else if (filter.getDate() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("date"), filter.getDate()));
            }

            // CancelledAt – range or exact
            if (filter.getCancelledAtFrom() != null || filter.getCancelledAtTo() != null) {
                specs.add(cancelledAtRange(filter.getCancelledAtFrom(), filter.getCancelledAtTo()));
            } else if (filter.getCancelledAt() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("cancelledAt"), filter.getCancelledAt()));
            }

            // CancelledBy – exact
            if (filter.getCancelledBy() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("cancelledBy").get("value"), filter.getCancelledBy()));
            }

            // Payment status – exact
            if (filter.getStatus() != null) {
                specs.add((root, query, cb) ->
                        cb.equal(root.get("status"), filter.getStatus()));
            }

            if (filter.getBranchId() != null) {
                specs.add(byBranchId(filter.getBranchId()));
            }

            return specs;
        }

        return specs;
    }

    // ---------- Private range helpers ----------
    private static Specification<PaymentJpaEntity> amountRange(BigDecimal from, BigDecimal to) {
        return (root, query, cb) ->
                buildRange(cb, root.get("amount").get("amount"), from, to);
    }

    private static Specification<PaymentJpaEntity> dateRange(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) ->
                buildRange(cb, root.get("date"), from, to);
    }

    private static Specification<PaymentJpaEntity> cancelledAtRange(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) ->
                buildRange(cb, root.get("cancelledAt"), from, to);
    }

    private static <T extends Comparable<? super T>> Predicate buildRange(CriteriaBuilder cb, Path<T> path, T from, T to) {
        if (from != null && to != null) {
            return cb.between(path, from, to);
        } else if (from != null) {
            return cb.greaterThanOrEqualTo(path, from);
        } else {
            return cb.lessThanOrEqualTo(path, to);  // to is guaranteed non‑null here
        }
    }

    private static Specification<PaymentJpaEntity> byBranchId(String branchId) {
        return (root, query, cb) -> {
            assert query != null;
            Subquery<BillId> subquery = query.subquery(BillId.class);
            Root<BillJpaEntity> billRoot = subquery.from(BillJpaEntity.class);
            subquery.select(billRoot.get("billId"))
                    .where(cb.equal(billRoot.get("branchId").get("id"), branchId));
            return cb.in(root.get("billId")).value(subquery);
        };
    }
}

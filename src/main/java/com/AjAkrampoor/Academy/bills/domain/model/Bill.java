package com.AjAkrampoor.Academy.bills.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bill {
    private BillId billId;
    private EnrollmentId enrollmentId;
    private Money amount;
    private Money discount;
    private Description description;
    private LocalDateTime createdAt;
    private BranchId branchId;
    private BillStatus status;
    private LocalDateTime cancelledAt;  // optional, for audit
    private UserId cancelledBy;

    public Bill(BillId billId, EnrollmentId enrollmentId, Money amount, Money discount, LocalDateTime createdAt, Description description, BranchId branchId, BillStatus status, LocalDateTime cancelledAt, UserId cancelledBy) {
        this.billId = Objects.requireNonNull(billId, "Bill ID cannot be null");
        this.enrollmentId = Objects.requireNonNull(enrollmentId, "Enrollment ID cannot be null");
        this.amount = Objects.requireNonNull(amount, "Payment amount cannot be null");
        this.discount = discount;
        this.description = description;
        this.createdAt = Objects.requireNonNull(createdAt, "Created at cannot be null");
        this.branchId = Objects.requireNonNull(branchId, "Branch ID must not be null");
        this.status = status;
        this.cancelledAt = cancelledAt;
        this.cancelledBy = cancelledBy;
        validateDiscountNotExceedsAmount();
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    private void validateDiscountNotExceedsAmount() {
        if (discount != null && discount.getAmount().compareTo(amount.getAmount()) > 0) {
            throw new IllegalArgumentException("Discount cannot exceed the bill amount");
        }
    }

    public void cancel(UserId cancelledBy) {
        if (this.status != BillStatus.ACTIVE) {
            throw new IllegalStateException("Only active bills can be cancelled");
        }
        this.status = BillStatus.CANCELLED;
        this.cancelledAt = LocalDateTime.now();
        this.cancelledBy = cancelledBy;
    }

    public BillId getBillId() {
        return billId;
    }

    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public Money getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Money getDiscount() {
        return discount;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public BillStatus getStatus() {
        return status;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public UserId getCancelledBy() {
        return cancelledBy;
    }
}

package com.AjAkrampoor.Academy.salaries.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDate;
import java.util.Objects;

public class SalaryPayment {

    private SalaryPaymentId id;
    private StaffId staffId;
    private SalaryMonth salaryMonth;
    private Money amount;
    private LocalDate paymentDate;
    private SalaryPaymentStatus status;
    private UserId createdBy;
    private LocalDate cancelledAt;

    public SalaryPayment(
            SalaryPaymentId id,
            StaffId staffId,
            SalaryMonth salaryMonth,
            Money amount,
            LocalDate paymentDate,
            SalaryPaymentStatus status,
            UserId createdBy
    ) {
        this.id = Objects.requireNonNull(id, "Salary payment ID must not be null");
        this.staffId = Objects.requireNonNull(staffId, "Staff ID must not be null");
        this.salaryMonth = Objects.requireNonNull(
                salaryMonth,
                "Salary month must not be null"
        );
        this.amount = Objects.requireNonNull(amount, "Salary payment amount must not be null");
        this.paymentDate = Objects.requireNonNull(
                paymentDate,
                "Salary payment date must not be null"
        );
        this.status = Objects.requireNonNull(
                status,
                "Salary payment status must not be null"
        );
        this.createdBy = Objects.requireNonNull(
                createdBy,
                "Created-by user ID must not be null"
        );
    }

    public void cancel() {
        if (this.status == SalaryPaymentStatus.CANCELLED) {
            throw new IllegalStateException("Salary payment is already cancelled");
        }

        this.status = SalaryPaymentStatus.CANCELLED;
        this.cancelledAt = LocalDate.now();
    }

    public boolean isPaid() {
        return this.status == SalaryPaymentStatus.PAID;
    }

    public boolean isCancelled() {
        return this.status == SalaryPaymentStatus.CANCELLED;
    }

    public SalaryPaymentId getId() {
        return id;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public SalaryMonth getSalaryMonth() {
        return salaryMonth;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public SalaryPaymentStatus getStatus() {
        return status;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public LocalDate getCancelledAt() {
        return cancelledAt;
    }
}
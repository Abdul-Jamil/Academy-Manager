package com.AjAkrampoor.Academy.salaries.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.time.LocalDate;
import java.util.Objects;

public class MonthlySalary {

    private MonthlySalaryId id;
    private StaffId staffId;
    private SalaryMonth salaryMonth;
    private Money earnedAmount;
    private Money paidAmount;
    private LocalDate finalizedAt;

    public MonthlySalary(
            MonthlySalaryId id,
            StaffId staffId,
            SalaryMonth salaryMonth,
            Money earnedAmount,
            Money paidAmount,
            LocalDate finalizedAt
    ) {
        this.id = Objects.requireNonNull(
                id,
                "Monthly salary ID must not be null"
        );
        this.staffId = Objects.requireNonNull(
                staffId,
                "Staff ID must not be null"
        );
        this.salaryMonth = Objects.requireNonNull(
                salaryMonth,
                "Salary month must not be null"
        );
        this.earnedAmount = Objects.requireNonNull(
                earnedAmount,
                "Earned salary amount must not be null"
        );
        this.paidAmount = Objects.requireNonNull(
                paidAmount,
                "Paid salary amount must not be null"
        );
        this.finalizedAt = Objects.requireNonNull(
                finalizedAt,
                "Finalized-at date must not be null"
        );

        if (paidAmount.getAmount().compareTo(earnedAmount.getAmount()) > 0) {
            throw new IllegalArgumentException(
                    "Paid salary cannot exceed earned salary"
            );
        }
    }

    public void recordPayment(Money paymentAmount) {
        Objects.requireNonNull(
                paymentAmount,
                "Payment amount must not be null"
        );

        Money newPaidAmount = this.paidAmount.add(paymentAmount);

        if (newPaidAmount.getAmount().compareTo(
                this.earnedAmount.getAmount()
        ) > 0) {
            throw new IllegalArgumentException(
                    "Paid salary cannot exceed earned salary"
            );
        }

        this.paidAmount = newPaidAmount;
    }

    public void reversePayment(Money paymentAmount) {
        Objects.requireNonNull(
                paymentAmount,
                "Payment amount must not be null"
        );

        this.paidAmount = this.paidAmount.subtract(paymentAmount);
    }

    public Money getRemainingAmount() {
        return this.earnedAmount.subtract(this.paidAmount);
    }

    public MonthlySalaryId getId() {
        return id;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public SalaryMonth getSalaryMonth() {
        return salaryMonth;
    }

    public Money getEarnedAmount() {
        return earnedAmount;
    }

    public Money getPaidAmount() {
        return paidAmount;
    }

    public LocalDate getFinalizedAt() {
        return finalizedAt;
    }
}

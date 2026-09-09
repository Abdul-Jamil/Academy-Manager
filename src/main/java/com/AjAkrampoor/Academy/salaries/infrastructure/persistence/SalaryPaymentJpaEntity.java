package com.AjAkrampoor.Academy.salaries.infrastructure.persistence;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentId;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentStatus;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "salary_payments")
public class SalaryPaymentJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(name = "salary_payment_id", nullable = false)
    )
    private SalaryPaymentId salaryPaymentId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "staff_id", nullable = false)
    )
    private StaffId staffId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "year",
                    column = @Column(name = "salary_year", nullable = false)
            ),
            @AttributeOverride(
                    name = "month",
                    column = @Column(name = "salary_month", nullable = false)
            )
    })
    private SalaryMonth salaryMonth;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(name = "amount", nullable = false, precision = 11, scale = 2)
    )
    private Money amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SalaryPaymentStatus status;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "created_by", nullable = false)
    )
    private UserId createdBy;

    @Column(name = "cancelled_at")
    private LocalDate cancelledAt;

    public SalaryPaymentJpaEntity() {
    }

    public SalaryPaymentId getSalaryPaymentId() {
        return salaryPaymentId;
    }

    public void setSalaryPaymentId(SalaryPaymentId salaryPaymentId) {
        this.salaryPaymentId = salaryPaymentId;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public void setStaffId(StaffId staffId) {
        this.staffId = staffId;
    }

    public SalaryMonth getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(SalaryMonth salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public SalaryPaymentStatus getStatus() {
        return status;
    }

    public void setStatus(SalaryPaymentStatus status) {
        this.status = status;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserId createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDate cancelledAt) {
        this.cancelledAt = cancelledAt;
    }
}
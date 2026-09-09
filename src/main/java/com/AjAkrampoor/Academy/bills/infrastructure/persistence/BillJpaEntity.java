package com.AjAkrampoor.Academy.bills.infrastructure.persistence;

import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.model.BillStatus;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity()
@Table(name = "bills")
public class BillJpaEntity {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    private BillId billId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "enrollment_id", nullable = false))
    private EnrollmentId enrollmentId;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(nullable = false))
    private Money amount;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "discount"))
    private Money discount;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private Description description;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "branch_id", nullable = false))
    private BranchId branchId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status = BillStatus.ACTIVE;

    private LocalDateTime cancelledAt;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "cancelled_by"))
    private UserId cancelledBy;


    public BillId getBillId() {
        return billId;
    }

    public void setBillId(BillId billId) {
        this.billId = billId;
    }

    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(EnrollmentId enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Money getDiscount() {
        return discount;
    }

    public void setDiscount(Money discount) {
        this.discount = discount;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchId branchId) {
        this.branchId = branchId;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public UserId getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(UserId cancelledBy) {
        this.cancelledBy = cancelledBy;
    }
}

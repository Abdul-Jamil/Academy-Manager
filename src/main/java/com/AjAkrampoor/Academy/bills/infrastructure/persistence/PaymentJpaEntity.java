package com.AjAkrampoor.Academy.bills.infrastructure.persistence;

import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.model.PaymentId;
import com.AjAkrampoor.Academy.bills.domain.model.PaymentStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity()
@Table(name = "payments")
public class PaymentJpaEntity {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    private PaymentId paymentId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "bill_id", nullable = false))
    private BillId billId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "received_by", nullable = false))
    private UserId receivedBy;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(nullable = false))
    private Money amount;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private Description description;
    @Column(nullable = false)
    private LocalDateTime date;
    private LocalDateTime cancelledAt;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "cancelled_by"))
    private UserId cancelledBy;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    public PaymentId getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(PaymentId paymentId) {
        this.paymentId = paymentId;
    }

    public BillId getBillId() {
        return billId;
    }

    public void setBillId(BillId billId) {
        this.billId = billId;
    }

    public UserId getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(UserId receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}

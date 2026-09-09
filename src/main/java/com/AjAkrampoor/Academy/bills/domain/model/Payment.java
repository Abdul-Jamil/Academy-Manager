package com.AjAkrampoor.Academy.bills.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private PaymentId paymentId;
    private BillId billId;
    private UserId receivedBy;
    private Money amount;
    private Description description;
    private LocalDateTime date;
    private LocalDateTime cancelledAt;
    private UserId cancelledBy;
    private PaymentStatus status;

    public Payment(PaymentId paymentId, BillId billId, UserId receivedBy, Money amount, Description description, LocalDateTime date, LocalDateTime cancelledAt, UserId cancelledBy, PaymentStatus status) {
        this.paymentId = Objects.requireNonNull(paymentId, "Payment ID cannot be null");
        this.billId = Objects.requireNonNull(billId, "Bill ID cannot be null");
        this.receivedBy = Objects.requireNonNull(receivedBy, "Receiver cannot be null");
        this.amount = Objects.requireNonNull(amount, "Payment amount cannot be null");
        this.description = description;
        this.date = Objects.requireNonNull(date, "Payment date cannot be null");
        this.cancelledAt = cancelledAt;
        this.cancelledBy = cancelledBy;
        this.status = Objects.requireNonNull(status, "Status Id cannot be null");
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public void cancel(UserId cancelledBy) {
        if (this.status == PaymentStatus.CANCELLED) {
            throw new IllegalStateException("Payment has already been cancelled");
        }
        this.status = PaymentStatus.CANCELLED;
        this.cancelledAt = LocalDateTime.now();
        this.cancelledBy = cancelledBy;
    }

    public PaymentId getPaymentId() {
        return paymentId;
    }

    public BillId getBillId() {
        return billId;
    }

    public UserId getReceivedBy() {
        return receivedBy;
    }

    public Money getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public UserId getCancelledBy() {
        return cancelledBy;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}

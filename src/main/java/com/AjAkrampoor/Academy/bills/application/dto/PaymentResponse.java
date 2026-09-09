package com.AjAkrampoor.Academy.bills.application.dto;

import com.AjAkrampoor.Academy.bills.domain.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {
    private String paymentId;
    private String billId;
    private String receivedBy;
    private BigDecimal amount;
    private String description;
    private LocalDateTime date;
    private LocalDateTime cancelledAt;
    private String cancelledBy;
    private PaymentStatus status;

    public PaymentResponse(String paymentId, String billId, String receivedBy, BigDecimal amount, String description, LocalDateTime date, LocalDateTime cancelledAt, String cancelledBy, PaymentStatus status) {
        this.paymentId = paymentId;
        this.billId = billId;
        this.receivedBy = receivedBy;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.cancelledAt = cancelledAt;
        this.cancelledBy = cancelledBy;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getBillId() {
        return billId;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}

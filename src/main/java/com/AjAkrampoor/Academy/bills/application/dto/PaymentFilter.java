package com.AjAkrampoor.Academy.bills.application.dto;

import com.AjAkrampoor.Academy.bills.domain.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentFilter {
    private String paymentId;
    private String billId;
    private String receivedBy;
    private BigDecimal amount;
    private String description;
    private LocalDateTime date;
    private LocalDateTime cancelledAt;
    private String cancelledBy;
    private PaymentStatus status;

    private BigDecimal amountFrom;
    private BigDecimal amountTo;

    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

    private LocalDateTime cancelledAtFrom;
    private LocalDateTime cancelledAtTo;

    private String branchId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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


    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
    }

    public BigDecimal getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public LocalDateTime getCancelledAtFrom() {
        return cancelledAtFrom;
    }

    public void setCancelledAtFrom(LocalDateTime cancelledAtFrom) {
        this.cancelledAtFrom = cancelledAtFrom;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public LocalDateTime getCancelledAtTo() {
        return cancelledAtTo;
    }

    public void setCancelledAtTo(LocalDateTime cancelledAtTo) {
        this.cancelledAtTo = cancelledAtTo;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}

package com.AjAkrampoor.Academy.salaries.application.dto;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryPaymentResponse {

    private final String salaryPaymentId;
    private final String staffName;
    private final String salaryMonth;
    private final BigDecimal amount;
    private final LocalDate paymentDate;
    private final SalaryPaymentStatus status;
    private final String createdBy;
    private final LocalDate cancelledAt;

    public SalaryPaymentResponse(
            String salaryPaymentId,
            String staffName,
            String salaryMonth,
            BigDecimal amount,
            LocalDate paymentDate,
            SalaryPaymentStatus status,
            String createdBy,
            LocalDate cancelledAt
    ) {
        this.salaryPaymentId = salaryPaymentId;
        this.staffName = staffName;
        this.salaryMonth = salaryMonth;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
        this.createdBy = createdBy;
        this.cancelledAt = cancelledAt;
    }

    public String getSalaryPaymentId() {
        return salaryPaymentId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getSalaryMonth() {
        return salaryMonth;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public SalaryPaymentStatus getStatus() {
        return status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDate getCancelledAt() {
        return cancelledAt;
    }
}
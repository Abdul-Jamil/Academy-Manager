package com.AjAkrampoor.Academy.salaries.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MonthlySalaryResponse {

    private final String monthlySalaryId;
    private final String staffName;
    private final String salaryMonth;
    private final BigDecimal earnedAmount;
    private final BigDecimal paidAmount;
    private final BigDecimal remainingAmount;
    private final LocalDate finalizedAt;

    public MonthlySalaryResponse(
            String monthlySalaryId,
            String staffName,
            String salaryMonth,
            BigDecimal earnedAmount,
            BigDecimal paidAmount,
            BigDecimal remainingAmount,
            LocalDate finalizedAt
    ) {
        this.monthlySalaryId = monthlySalaryId;
        this.staffName = staffName;
        this.salaryMonth = salaryMonth;
        this.earnedAmount = earnedAmount;
        this.paidAmount = paidAmount;
        this.remainingAmount = remainingAmount;
        this.finalizedAt = finalizedAt;
    }

    public String getMonthlySalaryId() {
        return monthlySalaryId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getSalaryMonth() {
        return salaryMonth;
    }

    public BigDecimal getEarnedAmount() {
        return earnedAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public LocalDate getFinalizedAt() {
        return finalizedAt;
    }
}
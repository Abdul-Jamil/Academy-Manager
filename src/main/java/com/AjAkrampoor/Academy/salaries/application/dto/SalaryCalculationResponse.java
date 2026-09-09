package com.AjAkrampoor.Academy.salaries.application.dto;

import java.math.BigDecimal;

public class SalaryCalculationResponse {

    private final String staffName;
    private final String salaryMonth;
    private final BigDecimal earnedAmount;
    private final BigDecimal paidAmount;
    private final BigDecimal availableAmount;
    private final BigDecimal projectedAmount;

    public SalaryCalculationResponse(
            String staffName,
            String salaryMonth,
            BigDecimal earnedAmount,
            BigDecimal paidAmount,
            BigDecimal availableAmount,
            BigDecimal projectedAmount
    ) {
        this.staffName = staffName;
        this.salaryMonth = salaryMonth;
        this.earnedAmount = earnedAmount;
        this.paidAmount = paidAmount;
        this.availableAmount = availableAmount;
        this.projectedAmount = projectedAmount;
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

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public BigDecimal getProjectedAmount() {
        return projectedAmount;
    }
}
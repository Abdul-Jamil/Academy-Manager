package com.AjAkrampoor.Academy.salaries.application;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.util.Objects;

public class SalaryCalculation {

    private final StaffId staffId;
    private final SalaryMonth salaryMonth;
    private final Money earnedAmount;
    private final Money paidAmount;
    private final Money availableAmount;
    private final Money projectedAmount;

    public SalaryCalculation(
            StaffId staffId,
            SalaryMonth salaryMonth,
            Money earnedAmount,
            Money paidAmount,
            Money availableAmount,
            Money projectedAmount
    ) {
        this.staffId = Objects.requireNonNull(staffId);
        this.salaryMonth = Objects.requireNonNull(salaryMonth);
        this.earnedAmount = Objects.requireNonNull(earnedAmount);
        this.paidAmount = Objects.requireNonNull(paidAmount);
        this.availableAmount = Objects.requireNonNull(availableAmount);
        this.projectedAmount = Objects.requireNonNull(projectedAmount);
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

    public Money getAvailableAmount() {
        return availableAmount;
    }

    public Money getProjectedAmount() {
        return projectedAmount;
    }
}
package com.AjAkrampoor.Academy.salaries.application.dto;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryContractResponse {

    private final String salaryContractId;
    private final String staffName;
    private final SalaryContractType type;
    private final BigDecimal rate;
    private final LocalDate effectiveFrom;
    private final LocalDate effectiveTo;

    public SalaryContractResponse(
            String salaryContractId,
            String staffName,
            SalaryContractType type,
            BigDecimal rate,
            LocalDate effectiveFrom,
            LocalDate effectiveTo
    ) {
        this.salaryContractId = salaryContractId;
        this.staffName = staffName;
        this.type = type;
        this.rate = rate;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
    }

    public String getSalaryContractId() {
        return salaryContractId;
    }

    public String getStaffName() {
        return staffName;
    }

    public SalaryContractType getType() {
        return type;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }
}
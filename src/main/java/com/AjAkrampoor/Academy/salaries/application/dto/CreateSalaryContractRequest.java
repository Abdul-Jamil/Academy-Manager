package com.AjAkrampoor.Academy.salaries.application.dto;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateSalaryContractRequest {

    @NotNull
    private String staffId;

    @NotNull
    private SalaryContractType type;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal rate;

    @NotNull
    private LocalDate effectiveFrom;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public SalaryContractType getType() {
        return type;
    }

    public void setType(SalaryContractType type) {
        this.type = type;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
}
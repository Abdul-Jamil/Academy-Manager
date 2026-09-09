package com.AjAkrampoor.Academy.salaries.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.time.LocalDate;
import java.util.Objects;

public class SalaryContract {

    private SalaryContractId id;
    private StaffId staffId;
    private SalaryContractType type;
    private Money rate;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    public SalaryContract(
            SalaryContractId id,
            StaffId staffId,
            SalaryContractType type,
            Money rate,
            LocalDate effectiveFrom
    ) {
        this(
                id,
                staffId,
                type,
                rate,
                effectiveFrom,
                null
        );
    }

    public SalaryContract(
            SalaryContractId id,
            StaffId staffId,
            SalaryContractType type,
            Money rate,
            LocalDate effectiveFrom,
            LocalDate effectiveTo
    ) {
        this.id = Objects.requireNonNull(id, "Salary contract ID must not be null");
        this.staffId = Objects.requireNonNull(staffId, "Staff ID must not be null");
        this.type = Objects.requireNonNull(type, "Salary contract type must not be null");
        this.rate = Objects.requireNonNull(rate, "Salary contract rate must not be null");
        this.effectiveFrom = Objects.requireNonNull(
                effectiveFrom,
                "Salary contract effective-from date must not be null"
        );

        if (effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new IllegalArgumentException(
                    "Salary contract effective-to date cannot be before effective-from date"
            );
        }

        this.effectiveTo = effectiveTo;
    }

    public void endOn(LocalDate effectiveTo) {
        Objects.requireNonNull(effectiveTo, "Salary contract effective-to date must not be null");

        if (effectiveTo.isBefore(this.effectiveFrom)) {
            throw new IllegalArgumentException(
                    "Salary contract effective-to date cannot be before effective-from date"
            );
        }

        if (this.effectiveTo != null) {
            throw new IllegalStateException("Salary contract has already ended");
        }

        this.effectiveTo = effectiveTo;
    }

    public boolean appliesOn(LocalDate date) {
        Objects.requireNonNull(date, "Date must not be null");

        boolean startsOnOrBefore = !date.isBefore(effectiveFrom);
        boolean endsOnOrAfter = effectiveTo == null || !date.isAfter(effectiveTo);

        return startsOnOrBefore && endsOnOrAfter;
    }

    public boolean isCurrentlyActive() {
        return appliesOn(LocalDate.now());
    }

    public SalaryContractId getId() {
        return id;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public SalaryContractType getType() {
        return type;
    }

    public Money getRate() {
        return rate;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }
}
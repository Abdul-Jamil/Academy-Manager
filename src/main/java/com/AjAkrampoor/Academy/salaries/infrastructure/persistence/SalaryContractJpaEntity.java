package com.AjAkrampoor.Academy.salaries.infrastructure.persistence;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractId;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractType;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "salary_contracts")
public class SalaryContractJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(name = "salary_contract_id", nullable = false)
    )
    private SalaryContractId salaryContractId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "staff_id", nullable = false)
    )
    private StaffId staffId;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", nullable = false)
    private SalaryContractType type;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(name = "rate_amount", nullable = false, precision = 11, scale = 2)
    )
    private Money rate;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    public SalaryContractJpaEntity() {
    }

    public SalaryContractId getSalaryContractId() {
        return salaryContractId;
    }

    public void setSalaryContractId(SalaryContractId salaryContractId) {
        this.salaryContractId = salaryContractId;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public void setStaffId(StaffId staffId) {
        this.staffId = staffId;
    }

    public SalaryContractType getType() {
        return type;
    }

    public void setType(SalaryContractType type) {
        this.type = type;
    }

    public Money getRate() {
        return rate;
    }

    public void setRate(Money rate) {
        this.rate = rate;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
}
package com.AjAkrampoor.Academy.salaries.infrastructure.persistence;

import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalaryId;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "monthly_salaries",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_monthly_salary_staff_month",
                        columnNames = {
                                "staff_id",
                                "salary_year",
                                "salary_month"
                        }
                )
        }
)
public class MonthlySalaryJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(name = "monthly_salary_id", nullable = false)
    )
    private MonthlySalaryId monthlySalaryId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "staff_id", nullable = false)
    )
    private StaffId staffId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "year",
                    column = @Column(name = "salary_year", nullable = false)
            ),
            @AttributeOverride(
                    name = "month",
                    column = @Column(name = "salary_month", nullable = false)
            )
    })
    private SalaryMonth salaryMonth;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(name = "earned_amount", nullable = false, precision = 11, scale = 2)
    )
    private Money earnedAmount;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(name = "paid_amount", nullable = false, precision = 11, scale = 2)
    )
    private Money paidAmount;

    @Column(name = "finalized_at", nullable = false)
    private LocalDate finalizedAt;

    public MonthlySalaryJpaEntity() {
    }

    public MonthlySalaryId getMonthlySalaryId() {
        return monthlySalaryId;
    }

    public void setMonthlySalaryId(MonthlySalaryId monthlySalaryId) {
        this.monthlySalaryId = monthlySalaryId;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public void setStaffId(StaffId staffId) {
        this.staffId = staffId;
    }

    public SalaryMonth getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(SalaryMonth salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public Money getEarnedAmount() {
        return earnedAmount;
    }

    public void setEarnedAmount(Money earnedAmount) {
        this.earnedAmount = earnedAmount;
    }

    public Money getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Money paidAmount) {
        this.paidAmount = paidAmount;
    }

    public LocalDate getFinalizedAt() {
        return finalizedAt;
    }

    public void setFinalizedAt(LocalDate finalizedAt) {
        this.finalizedAt = finalizedAt;
    }
}
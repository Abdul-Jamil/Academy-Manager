package com.AjAkrampoor.Academy.inventory.infrastructure.persistence;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleId;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
public class SaleJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "sale_id",
                    nullable = false,
                    updatable = false,
                    unique = true
            )
    )
    private SaleId saleId;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "branch_id",
                    nullable = false,
                    updatable = false
            )
    )
    private BranchId branchId;

    @Embedded
    @AttributeOverride(
            name = "amount",
            column = @Column(
                    name = "total_amount",
                    nullable = false
            )
    )
    private Money totalAmount;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "discount_amount", nullable = false))
    private Money discountAmount;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "sold_by",
                    nullable = false,
                    updatable = false
            )
    )
    private UserId soldBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime soldAt;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "description")
    )
    private Description description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus saleStatus;

    public SaleJpaEntity() {
    }

    public SaleId getSaleId() {
        return saleId;
    }

    public void setSaleId(SaleId saleId) {
        this.saleId = saleId;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchId branchId) {
        this.branchId = branchId;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Money getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Money discountAmount) {
        this.discountAmount = discountAmount;
    }

    public UserId getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(UserId soldBy) {
        this.soldBy = soldBy;
    }

    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDateTime soldAt) {
        this.soldAt = soldAt;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}

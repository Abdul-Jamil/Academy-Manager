package com.AjAkrampoor.Academy.inventory.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.users.domain.model.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Sale {

    private SaleId saleId;
    private BranchId branchId;
    private Money totalAmount;
    private Money discountAmount;
    private UserId soldBy;
    private LocalDateTime soldAt;
    private Description description;
    private SaleStatus saleStatus;

    public Sale(
            SaleId saleId,
            BranchId branchId,
            Money totalAmount, Money discountAmount,
            UserId soldBy,
            LocalDateTime soldAt, Description description,
            SaleStatus saleStatus
    ) {
        this.saleId = Objects.requireNonNull(saleId);
        this.branchId = Objects.requireNonNull(branchId);
        this.totalAmount = Objects.requireNonNull(totalAmount);
        this.discountAmount = discountAmount;
        this.soldBy = Objects.requireNonNull(soldBy);
        this.soldAt = Objects.requireNonNull(soldAt);
        this.description = description;
        this.saleStatus = Objects.requireNonNull(saleStatus);
    }

    public void cancel() {
        if (saleStatus == SaleStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Sale is already cancelled"
            );
        }

        saleStatus = SaleStatus.CANCELLED;
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public SaleId getSaleId() {
        return saleId;
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public Money getDiscountAmount() {
        return discountAmount;
    }

    public UserId getSoldBy() {
        return soldBy;
    }

    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public Description getDescription() {
        return description;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }
}

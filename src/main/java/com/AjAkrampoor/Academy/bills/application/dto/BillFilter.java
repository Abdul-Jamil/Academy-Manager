package com.AjAkrampoor.Academy.bills.application.dto;

import com.AjAkrampoor.Academy.bills.domain.model.BillStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillFilter {
    private String id;

    // Exact match
    private BigDecimal amount;
    private BigDecimal discount;
    private LocalDateTime createdAt;

    private String studentId;
    private String studentFirstName;
    private String studentLastName;

    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private BigDecimal discountFrom;
    private BigDecimal discountTo;
    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;

    private String description;
    private String branchId;

    private BillStatus status;
    private Boolean hasRemainingAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
    }

    public BigDecimal getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
    }

    public BigDecimal getDiscountFrom() {
        return discountFrom;
    }

    public void setDiscountFrom(BigDecimal discountFrom) {
        this.discountFrom = discountFrom;
    }

    public BigDecimal getDiscountTo() {
        return discountTo;
    }

    public void setDiscountTo(BigDecimal discountTo) {
        this.discountTo = discountTo;
    }

    public LocalDateTime getCreatedAtFrom() {
        return createdAtFrom;
    }

    public void setCreatedAtFrom(LocalDateTime createdAtFrom) {
        this.createdAtFrom = createdAtFrom;
    }

    public LocalDateTime getCreatedAtTo() {
        return createdAtTo;
    }

    public void setCreatedAtTo(LocalDateTime createdAtTo) {
        this.createdAtTo = createdAtTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public Boolean getHasRemainingAmount() {
        return hasRemainingAmount;
    }

    public void setHasRemainingAmount(Boolean hasRemainingAmount) {
        this.hasRemainingAmount = hasRemainingAmount;
    }
}

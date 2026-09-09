package com.AjAkrampoor.Academy.income.application.dto;

import com.AjAkrampoor.Academy.income.domain.model.ProfitStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IncomeResponse {
    private String profitId;
    private String description;
    private BigDecimal amount;
    private String createdBy;
    private LocalDateTime createdAt;
    private String profitCategoryName;
    private ProfitStatus profitStatus;
    private String deletedBy;
    private LocalDateTime deletedAt;
    private String branchName;

    public IncomeResponse(String profitId, String description, BigDecimal amount, String createdBy,
                          LocalDateTime createdAt, String profitCategoryName, ProfitStatus profitStatus,
                          String deletedBy, LocalDateTime deletedAt, String branchName) {
        this.profitId = profitId;
        this.description = description;
        this.amount = amount;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.profitCategoryName = profitCategoryName;
        this.profitStatus = profitStatus;
        this.deletedBy = deletedBy;
        this.deletedAt = deletedAt;
        this.branchName = branchName;
    }

    // getters
    public String getProfitId() {
        return profitId;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getProfitCategoryName() {
        return profitCategoryName;
    }

    public ProfitStatus getProfitStatus() {
        return profitStatus;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public String getBranchName() {
        return branchName;
    }
}

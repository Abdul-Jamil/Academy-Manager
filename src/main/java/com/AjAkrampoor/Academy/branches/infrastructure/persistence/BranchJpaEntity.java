package com.AjAkrampoor.Academy.branches.infrastructure.persistence;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.model.BranchName;
import com.AjAkrampoor.Academy.branches.domain.model.BranchStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "branch")
public class BranchJpaEntity {

    @EmbeddedId()
    private BranchId branchId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "branch_name", nullable = false, unique = true))
    })
    private BranchName branchName;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch_status", nullable = false)
    private BranchStatus branchStatus;

    @Column(nullable = false)
    private boolean isDefault;

    public BranchJpaEntity() {
        // For JPA
    }

    public BranchId getBranchId() {
        return branchId;
    }

    public void setBranchId(BranchId branchId) {
        this.branchId = branchId;
    }

    public BranchName getBranchName() {
        return branchName;
    }

    public void setBranchName(BranchName branchName) {
        this.branchName = branchName;
    }

    public BranchStatus getBranchStatus() {
        return branchStatus;
    }

    public void setBranchStatus(BranchStatus branchStatus) {
        this.branchStatus = branchStatus;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}

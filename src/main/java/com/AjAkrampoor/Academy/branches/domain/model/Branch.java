package com.AjAkrampoor.Academy.branches.domain.model;

public class Branch {
    private BranchId id;
    private BranchName name;
    private BranchStatus status;
    private boolean isDefault;

    public Branch(BranchId id, BranchName name, BranchStatus status, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.isDefault = isDefault;
    }

    public void rename(BranchName newName) {
        if (newName == null) {
            throw new IllegalArgumentException("Please enter a valid branch name.");
        }
        this.name = newName;
    }

    public void deactivate() {
        if (this.status == BranchStatus.INACTIVE) {
            return;
        }
        this.status = BranchStatus.INACTIVE;
    }

    public void reactivate() {
        if (this.status == BranchStatus.ACTIVE) {
            return;
        }
        this.status = BranchStatus.ACTIVE;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isActive() {
        return this.status == BranchStatus.ACTIVE;
    }

    public BranchId getId() {
        return id;
    }

    public BranchName getName() {
        return name;
    }

    public BranchStatus getStatus() {
        return status;
    }

}

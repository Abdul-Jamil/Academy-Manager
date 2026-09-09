package com.AjAkrampoor.Academy.branches.application.dto;

import com.AjAkrampoor.Academy.branches.domain.model.BranchStatus;

public class BranchResponse {

    private String id;
    private String name;
    private BranchStatus status;
    private boolean isDefault;

    public BranchResponse(String id, String name, BranchStatus status, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.isDefault = isDefault;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BranchStatus getStatus() {
        return status;
    }

    public boolean isDefault() {
        return isDefault;
    }
}

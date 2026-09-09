package com.AjAkrampoor.Academy.branches.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BranchRequest {
    @NotBlank
    @Size(min = 3, max = 100, message = "Branch name must be between 3 and 100 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

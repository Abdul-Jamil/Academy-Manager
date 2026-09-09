package com.AjAkrampoor.Academy.courses.application.dto;

import jakarta.validation.constraints.Size;

public class UpdateClassDescriptionRequest {

    @Size(min = 3, max = 200)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

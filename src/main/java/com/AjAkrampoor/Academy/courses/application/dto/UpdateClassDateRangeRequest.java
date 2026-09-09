package com.AjAkrampoor.Academy.courses.application.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UpdateClassDateRangeRequest {

    @NotNull
    private LocalDate startDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}

package com.AjAkrampoor.Academy.courses.application.dto;

import com.AjAkrampoor.Academy.courses.domain.model.ClassStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ChangeClassStatusRequest {

    @NotNull
    private ClassStatus status;

    private LocalDate startDate;

    public ClassStatus getStatus() {
        return status;
    }

    public void setStatus(ClassStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}

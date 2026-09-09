package com.AjAkrampoor.Academy.courses.application.dto;

import java.time.LocalDate;

public class ClassScheduleCreateRequest {

    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
}

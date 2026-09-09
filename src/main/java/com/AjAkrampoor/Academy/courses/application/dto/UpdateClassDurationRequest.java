package com.AjAkrampoor.Academy.courses.application.dto;

import jakarta.validation.constraints.Min;

public class UpdateClassDurationRequest {

    @Min(value = 1, message = "Duration cannot be less than 1")
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

package com.AjAkrampoor.Academy.courses.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class DurationDays {
    private int duration;

    private DurationDays() {
    }

    public DurationDays(int duration) {
        if (duration < 1) {
            throw new IllegalArgumentException("Duration cannot be less than 1");
        }
        this.duration = duration;
    }

    public DurationDays addDays(int addedDays) {
        if (addedDays < 1) {
            throw new IllegalArgumentException("cannot add less than 1 days");
        }
        int i = this.duration + addedDays;
        return new DurationDays(i);
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DurationDays that = (DurationDays) o;
        return duration == that.duration;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(duration);
    }
}

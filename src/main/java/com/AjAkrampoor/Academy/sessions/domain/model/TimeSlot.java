package com.AjAkrampoor.Academy.sessions.domain.model;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;
import java.util.Objects;

@Embeddable
public class TimeSlot {

    private LocalTime startTime;
    private LocalTime endTime;

    private TimeSlot() {
    }

    public TimeSlot(
            LocalTime startTime,
            LocalTime endTime
    ) {
        Objects.requireNonNull(
                startTime,
                "Start time must not be null"
        );

        Objects.requireNonNull(
                endTime,
                "End time must not be null"
        );

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException(
                    "End time cannot be before start time"
            );
        }

        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSlot timeSlot = (TimeSlot) o;

        return Objects.equals(startTime, timeSlot.startTime)
                && Objects.equals(endTime, timeSlot.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    @Override
    public String toString() {
        return startTime + " - " + endTime;
    }
}

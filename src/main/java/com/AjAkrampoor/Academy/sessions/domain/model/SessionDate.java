package com.AjAkrampoor.Academy.sessions.domain.model;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class SessionDate {

    private LocalDate date;

    private SessionDate() {
    }

    public SessionDate(LocalDate date) {
        this.date = Objects.requireNonNull(
                date,
                "Session date must not be null"
        );
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionDate that = (SessionDate) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return date.toString();
    }
}

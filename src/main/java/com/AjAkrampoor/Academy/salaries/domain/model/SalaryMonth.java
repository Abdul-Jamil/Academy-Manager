package com.AjAkrampoor.Academy.salaries.domain.model;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

@Embeddable
public class SalaryMonth {

    private int year;
    private int month;

    protected SalaryMonth() {
    }

    public SalaryMonth(int year, int month) {
        validate(year, month);
        this.year = year;
        this.month = month;
    }

    public static SalaryMonth from(YearMonth yearMonth) {
        Objects.requireNonNull(yearMonth, "Salary month cannot be null");
        return new SalaryMonth(yearMonth.getYear(), yearMonth.getMonthValue());
    }

    public static SalaryMonth of(int year, int month) {
        return new SalaryMonth(year, month);
    }

    public static SalaryMonth current() {
        return from(YearMonth.now());
    }

    public YearMonth toYearMonth() {
        return YearMonth.of(year, month);
    }

    public LocalDate getStartDate() {
        return toYearMonth().atDay(1);
    }

    public LocalDate getEndDate() {
        return toYearMonth().atEndOfMonth();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public boolean isBefore(SalaryMonth other) {
        Objects.requireNonNull(other, "Salary month cannot be null");
        return toYearMonth().isBefore(other.toYearMonth());
    }

    public boolean isAfter(SalaryMonth other) {
        Objects.requireNonNull(other, "Salary month cannot be null");
        return toYearMonth().isAfter(other.toYearMonth());
    }

    public boolean isCurrent() {
        return this.equals(current());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalaryMonth that = (SalaryMonth) o;

        return year == that.year && month == that.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d", year, month);
    }

    private static void validate(int year, int month) {
        if (year < 1) {
            throw new IllegalArgumentException("Salary year must be positive");
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Salary month must be between 1 and 12");
        }
    }
}
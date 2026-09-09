package com.AjAkrampoor.Academy.shared.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class PhoneNumber {
    private String number;

    private PhoneNumber() {
    }

    public PhoneNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (number.length() < 10) {
            throw new IllegalArgumentException("Phone number cannot be less than 10 characters");
        }
        if (!number.matches("^\\+?[0-9]+$")) {
            throw new IllegalArgumentException("Phone number can only contain digits and an optional '+' at the beginning");
        }

        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return number;
    }
}

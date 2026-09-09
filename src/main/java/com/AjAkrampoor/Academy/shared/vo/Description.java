package com.AjAkrampoor.Academy.shared.vo;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Description {
    private String value;

    private Description() {
    }

    public Description(String value) {
        if (value == null || value.trim().length() < 3) {
            throw new IllegalArgumentException("Description cannot be less than 3 letters");
        }
        if (value.length() > 200) {
            throw new IllegalArgumentException("Description cannot be more than 200 letters");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

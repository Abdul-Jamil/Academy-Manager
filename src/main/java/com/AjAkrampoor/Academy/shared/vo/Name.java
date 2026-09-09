package com.AjAkrampoor.Academy.shared.vo;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Name {
    private String firstName;
    private String lastName;

    private Name() {
    }

    public Name(String firstName, String lastName) {
        firstName = firstName == null ? null : firstName.trim();
        lastName = lastName == null ? null : lastName.trim();

        validateFirstName(firstName);
        validateLastName(lastName);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Name withFirstName(String newFirstName) {
        return new Name(newFirstName, this.lastName);
    }

    public Name withLastName(String newLastName) {
        return new Name(this.firstName, newLastName);
    }

    private void validateFirstName(String firstName) {
        validateNamePart(firstName, "First name");
    }

    private void validateLastName(String lastName) {
        validateNamePart(lastName, "Last name");
    }

    private void validateNamePart(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        if (value.length() < 2 || value.length() > 50) {
            throw new IllegalArgumentException(
                    fieldName + " must be between 2 and 50 characters");
        }
        if (!value.matches("^[\\p{L}\\s\\-']+$")) {
            throw new IllegalArgumentException(
                    fieldName + " can only contain letters, spaces, hyphens, and apostrophes");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return firstName.equals(name.firstName) && lastName.equals(name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
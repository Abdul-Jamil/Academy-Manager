package com.AjAkrampoor.Academy.students.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.Year;
import java.util.Objects;

@Embeddable
public class StudentId implements Serializable {
    private static final String PREFIX = "STDNT";
    private static final int RANDOM_PART_LENGTH = 6;
    // Explicit pool excluding: 0, 1, 5, I, O, S
    private static final String ALLOWED_CHARS = "2346789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String VALIDATION_REGEX = String.format("^\\d{4}-%s-[2346789ABCDEFGHJKLMNPQRSTUVWXYZ]{%d}$", PREFIX, RANDOM_PART_LENGTH);

    private String id;

    private StudentId() {
    }

    StudentId(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid StudentId format: " + value);
        }
        this.id = value;
    }

    public static StudentId generate() {
        int year = Year.now().getValue();
        String randomPart = generateRandomString();
        return new StudentId(String.format("%04d-%s-%s", year, PREFIX, randomPart));
    }

    public static StudentId from(String value) {
        return new StudentId(value);
    }

    private static String generateRandomString() {
        StringBuilder sb = new StringBuilder(RANDOM_PART_LENGTH);
        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            sb.append(ALLOWED_CHARS.charAt(RANDOM.nextInt(ALLOWED_CHARS.length())));
        }
        return sb.toString();
    }

    public static boolean isValid(String id) {
        return id != null && id.matches(VALIDATION_REGEX);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentId studentId = (StudentId) o;
        return Objects.equals(id, studentId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}

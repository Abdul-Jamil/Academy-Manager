package com.AjAkrampoor.Academy.staff.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.Year;
import java.util.Objects;

@Embeddable
public class StaffId implements Serializable {
    private static final String PREFIX = "STAFF";
    private static final int RANDOM_PART_LENGTH = 6;
    private static final String ALLOWED_CHARS = "2346789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String VALIDATION_REGEX = String.format("^\\d{4}-%s-[2346789ABCDEFGHJKLMNPQRSTUVWXYZ]{%d}$", PREFIX, RANDOM_PART_LENGTH);

    private String id;

    private StaffId() {
    }

    public StaffId(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid StaffId format: " + value);
        }
        this.id = value;
    }

    public static StaffId generate() {
        int year = Year.now().getValue();
        String randomPart = generateRandomString();
        return new StaffId(String.format("%04d-%s-%s", year, PREFIX, randomPart));
    }

    public static StaffId from(String value) {
        return new StaffId(value);
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
        StaffId staffId = (StaffId) o;
        return Objects.equals(id, staffId.id);
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


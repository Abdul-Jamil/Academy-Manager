package com.AjAkrampoor.Academy.users.domain.model;

import jakarta.persistence.Embeddable;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Objects;

@Embeddable
public class Password {
    private String value;

    private Password() {
    }

    public static Password createFromRaw(String rawPassword) {
        if (rawPassword == null || rawPassword.trim().length() < 5) {
            throw new IllegalArgumentException("Password must be at least 5 characters");
        }
        Password password = new Password();
        password.value = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
        return password;
    }

    public boolean matches(String candidateRawPassword) {
        return candidateRawPassword != null && this.value != null && BCrypt.checkpw(candidateRawPassword, this.value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

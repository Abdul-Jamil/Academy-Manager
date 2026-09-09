package com.AjAkrampoor.Academy.sessions.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SessionId implements Serializable {

    private String id;

    private SessionId() {
    }

    public SessionId(UUID value) {
        this.id = String.valueOf(
                Objects.requireNonNull(value, "Session ID cannot be null")
        );
    }

    public static SessionId newId() {
        return new SessionId(UUID.randomUUID());
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionId sessionId = (SessionId) o;
        return Objects.equals(id, sessionId.id);
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

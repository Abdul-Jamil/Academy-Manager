package com.AjAkrampoor.Academy.sessions.application.usecases.session;

import com.AjAkrampoor.Academy.sessions.domain.model.Session;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionId;
import com.AjAkrampoor.Academy.sessions.domain.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CancelSessionUseCase {

    private final SessionRepository sessionRepository;

    public CancelSessionUseCase(
            SessionRepository sessionRepository
    ) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public Session execute(UUID sessionId) {

        Session session = sessionRepository.findById(new SessionId(sessionId))
                .orElseThrow(() -> new IllegalArgumentException("No such session found"));

        session.cancel();

        return sessionRepository.save(session);
    }
}

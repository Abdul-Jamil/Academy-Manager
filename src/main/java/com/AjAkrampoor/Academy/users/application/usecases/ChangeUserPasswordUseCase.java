package com.AjAkrampoor.Academy.users.application.usecases;

import com.AjAkrampoor.Academy.users.domain.model.Password;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeUserPasswordUseCase {

    private final UserRepository repository;

    public ChangeUserPasswordUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User execute(String userId, String password) {
        User user = repository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));

        Password pass = Password.createFromRaw(password);
        user.changePassword(pass);

        return repository.save(user);
    }
}

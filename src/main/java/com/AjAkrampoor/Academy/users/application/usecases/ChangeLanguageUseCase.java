package com.AjAkrampoor.Academy.users.application.usecases;

import com.AjAkrampoor.Academy.users.domain.model.Language;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeLanguageUseCase {
    private final UserRepository userRepository;

    public ChangeLanguageUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User execute(String userUUID, Language language) {
        User user = userRepository.findById(UserId.fromString(userUUID)).orElseThrow(() -> new IllegalArgumentException("No such user found"));

        if (user.getLanguage().equals(language)) {
            return user;
        }

        user.changeLanguage(language);
        return userRepository.save(user);
    }
}

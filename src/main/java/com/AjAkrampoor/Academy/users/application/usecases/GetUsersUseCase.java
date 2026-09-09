package com.AjAkrampoor.Academy.users.application.usecases;

import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUsersUseCase {
    private final UserRepository repository;

    public GetUsersUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> execute() {
        return repository.findAll();
    }
}

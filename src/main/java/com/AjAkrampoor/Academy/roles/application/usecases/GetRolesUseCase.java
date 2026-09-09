package com.AjAkrampoor.Academy.roles.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRolesUseCase {
    private final RoleRepository repository;

    public GetRolesUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> execute() {
        return repository.findAll();
    }
}

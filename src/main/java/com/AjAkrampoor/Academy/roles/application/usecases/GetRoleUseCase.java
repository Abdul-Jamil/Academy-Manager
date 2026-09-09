package com.AjAkrampoor.Academy.roles.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class GetRoleUseCase {

    private final RoleRepository repository;

    public GetRoleUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    public Role execute(String id) {
        return repository.findById(RoleId.fromString(id)).orElseThrow(() -> new IllegalArgumentException("No such role found"));
    }
}

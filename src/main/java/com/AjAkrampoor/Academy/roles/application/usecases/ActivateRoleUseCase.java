package com.AjAkrampoor.Academy.roles.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ActivateRoleUseCase {

    private final RoleRepository repository;

    public ActivateRoleUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Role execute(String roleId) {
        Role role = repository.findById(RoleId.fromString(roleId)).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        if (role.isActive()) {
            return role;
        }
        role.reactivate();
        return repository.save(role);
    }
}

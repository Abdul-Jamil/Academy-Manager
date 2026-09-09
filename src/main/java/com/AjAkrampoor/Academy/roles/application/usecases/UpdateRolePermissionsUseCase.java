package com.AjAkrampoor.Academy.roles.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.Permission;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UpdateRolePermissionsUseCase {
    private final RoleRepository repository;

    public UpdateRolePermissionsUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Role execute(Set<Permission> newPermissions, String id) {
        Role role = repository.findById(RoleId.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (role.isDefault()) {
            throw new IllegalArgumentException("Cannot change default system role's permissions");
        }
        role.replacePermissions(newPermissions);

        return repository.save(role);
    }
}

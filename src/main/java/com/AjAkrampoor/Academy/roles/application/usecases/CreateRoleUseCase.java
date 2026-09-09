package com.AjAkrampoor.Academy.roles.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.*;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CreateRoleUseCase {
    private final RoleRepository repository;
    private static final int MAX_RETRIES = 10;

    public CreateRoleUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Role execute(String name, Set<Permission> permissionList) {
        RoleName roleName = new RoleName(name);
        if (repository.existsByRoleName(roleName)) {
            throw new IllegalArgumentException("Role with this name already exists");
        }

        RoleId roleId;
        for (int i = 0; i < MAX_RETRIES; i++) {
            roleId = RoleId.newId();
            if (!repository.existsById(roleId)) {
                Role role = new Role(roleId, roleName, permissionList, RoleStatus.ACTIVE, false);
                return repository.save(role);

            }
        }
        throw new IllegalArgumentException("Failed to create role after several retries, please try again later");
    }
}

package com.AjAkrampoor.Academy.roles.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.model.RoleName;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ChangeRoleNameUseCase {
    private final RoleRepository repository;

    public ChangeRoleNameUseCase(RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Role execute(String roleId, String newName) {
        RoleId id = RoleId.fromString(roleId);
        Role role = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (role.isDefault()) {
            throw new IllegalArgumentException("Cannot change default system role's name");
        }

        String currentName = role.getName().getValue();
        if (currentName.equals(newName)) {
            return role;
        }

        RoleName roleName = new RoleName(newName);
        if (repository.existsByRoleName(roleName)) {
            throw new IllegalArgumentException("This role name already exists!");
        }

        role.rename(newName);
        return repository.save(role);
    }
}

package com.AjAkrampoor.Academy.roles.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeactivateRoleUseCase {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public DeactivateRoleUseCase(RoleRepository repository, UserRepository userRepository) {
        this.roleRepository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Role execute(String roleId) {
        Role role = roleRepository.findById(RoleId.fromString(roleId)).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        if (!role.isActive()) {
            return role;
        }
        if (role.isDefault()) {
            throw new IllegalArgumentException("Cannot disable default system role");
        }
        if (userRepository.countByRoleId(RoleId.fromString(roleId)) > 0) {
            throw new IllegalArgumentException("Cannot delete role because it is assigned to users");
        }

        role.deactivate();
        return roleRepository.save(role);
    }
}

package com.AjAkrampoor.Academy.users.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserRoleUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UpdateUserRoleUseCase(UserRepository repository, RoleRepository roleRepository) {
        this.userRepository = repository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User execute(UUID userUUID, UUID roleUUID) {
        final String SUPERADMIN = "SUPERADMIN";

        UserId userId = new UserId(userUUID);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No such user found"));

        Role currentRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (currentRole.getName().toString().equals(SUPERADMIN)) {
            throw new IllegalArgumentException("Cannot change root admin role");
        }

        Role newRole = roleRepository.findById(new RoleId(roleUUID)).orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (!newRole.isActive()) {
            throw new IllegalArgumentException("Cannot assign inactive role to user");
        }

        if (newRole.getName().getValue().equals(SUPERADMIN)) {
            throw new IllegalArgumentException("Cannot assign root admin role to users");
        }

        if (user.getRoleId().equals(newRole.getId())) {
            return user;
        }

        user.changeRole(newRole.getId());
        return userRepository.save(user);
    }
}

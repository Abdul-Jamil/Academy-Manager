package com.AjAkrampoor.Academy.roles.domain.repository;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.model.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findById(RoleId id);

    Role save(Role role);

    boolean existsByRoleName(RoleName roleName);

    boolean existsById(RoleId roleId);

    List<Role> findAll();
}

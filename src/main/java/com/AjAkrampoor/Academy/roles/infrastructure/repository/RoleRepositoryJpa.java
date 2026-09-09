package com.AjAkrampoor.Academy.roles.infrastructure.repository;

import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.model.RoleName;
import com.AjAkrampoor.Academy.roles.infrastructure.persistence.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositoryJpa extends JpaRepository<RoleJpaEntity, RoleId> {
    boolean existsByName(RoleName roleName);
}

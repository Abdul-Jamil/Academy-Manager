package com.AjAkrampoor.Academy.roles.presentation.mapper;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.infrastructure.persistence.RoleJpaEntity;

import java.util.List;

public class RoleMapper {


    public static Role toDomain(RoleJpaEntity entity) {
        return new Role(entity.getId(), entity.getName(), entity.getPermissions(), entity.getRoleStatus(), entity.isDefault());
    }


    public static RoleJpaEntity toJpaEntity(Role role) {
        RoleJpaEntity roleJpaEntity = new RoleJpaEntity();
        roleJpaEntity.setId(role.getId());
        roleJpaEntity.setName(role.getName());
        roleJpaEntity.setPermissions(role.getPermissions());
        roleJpaEntity.setRoleStatus(role.getRoleStatus());
        roleJpaEntity.setDefault(role.isDefault());

        return roleJpaEntity;
    }

    public static List<Role> toDomainList(List<RoleJpaEntity> entities) {
        return entities.stream()
                .map(RoleMapper::toDomain)
                .toList();
    }
}

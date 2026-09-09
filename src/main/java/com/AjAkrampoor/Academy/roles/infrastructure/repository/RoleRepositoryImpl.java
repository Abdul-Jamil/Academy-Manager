package com.AjAkrampoor.Academy.roles.infrastructure.repository;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.model.RoleName;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.roles.infrastructure.persistence.RoleJpaEntity;
import com.AjAkrampoor.Academy.roles.presentation.mapper.RoleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleRepositoryJpa repository;

    public RoleRepositoryImpl(RoleRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> findById(RoleId id) {
        return repository.findById(id).map(RoleMapper::toDomain);
    }

    @Override
    public Role save(Role role) {
        repository.save(RoleMapper.toJpaEntity(role));
        return role;
    }

    @Override
    public boolean existsByRoleName(RoleName roleName) {
        return repository.existsByName(roleName);
    }

    @Override
    public boolean existsById(RoleId roleId) {
        return repository.existsById(roleId);
    }

    @Override
    public List<Role> findAll() {
        List<RoleJpaEntity> all = repository.findAll();
        return RoleMapper.toDomainList(all);
    }

}

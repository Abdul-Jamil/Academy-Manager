package com.AjAkrampoor.Academy.users.infrastructure.repository;

import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.model.UserName;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import com.AjAkrampoor.Academy.users.infrastructure.persistence.UserJpaEntity;
import com.AjAkrampoor.Academy.users.presentation.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa repository;

    public UserRepositoryImpl(UserRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return repository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByUserName(UserName userName) {
        return repository.existsByUserName(userName);
    }

    @Override
    public Optional<User> findByUserName(UserName userName) {
        return Optional.ofNullable(repository.findByUserName(userName))
                .map(UserMapper::toDomain);
    }

    @Override
    public boolean existsById(UserId id) {
        return repository.existsById(id);
    }

    @Override
    public User save(User user) {
        repository.save(UserMapper.toJpaEntity(user));
        return user;
    }

    @Override
    public List<User> findAll() {
        List<UserJpaEntity> all = repository.findAll();
        return all.stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public long countByRoleId(RoleId roleId) {
        return repository.countByRoleId(roleId);
    }

    @Override
    public Optional<User> findByStaffId(StaffId id) {
        return Optional.ofNullable(repository.findByStaffId(id))
                .map(UserMapper::toDomain);
    }
}

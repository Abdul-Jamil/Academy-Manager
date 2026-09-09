package com.AjAkrampoor.Academy.users.presentation.mapper;

import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.infrastructure.persistence.UserJpaEntity;

public class UserMapper {

    public static User toDomain(UserJpaEntity entity) {
        return new User
                (
                        entity.getUserId(),
                        entity.getUserName(),
                        entity.getPassword(),
                        entity.getLanguage(),
                        entity.getRoleId(),
                        entity.getStaffId()
                );
    }

    public static UserJpaEntity toJpaEntity(User domain) {
        UserJpaEntity entity = new UserJpaEntity();

        entity.setUserId(domain.getUserId());
        entity.setUserName(domain.getUserName());
        entity.setPassword(domain.getPassword());
        entity.setLanguage(domain.getLanguage());
        entity.setRoleId(domain.getRoleId());
        entity.setStaffId(domain.getStaffId());
        return entity;
    }
}
package com.AjAkrampoor.Academy.users.infrastructure.repository;

import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.model.UserName;
import com.AjAkrampoor.Academy.users.infrastructure.persistence.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpa extends JpaRepository<UserJpaEntity, UserId> {
    boolean existsByUserName(UserName userName);

    UserJpaEntity findByUserName(UserName userName);

    long countByRoleId(RoleId roleId);

    UserJpaEntity findByStaffId(StaffId staffId);
}

package com.AjAkrampoor.Academy.users.domain.repository;

import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.model.UserName;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId id);

    boolean existsByUserName(UserName userName);

    Optional<User> findByUserName(UserName userName);

    boolean existsById(UserId id);

    User save(User user);

    List<User> findAll();

    long countByRoleId(RoleId roleId);

    Optional<User> findByStaffId(StaffId id);

}

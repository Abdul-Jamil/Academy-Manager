package com.AjAkrampoor.Academy.users.domain.model;

import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.util.Objects;

public class User {
    private final UserId userId;
    private final UserName userName;
    private Password password;
    private Language language;

    private RoleId roleId;
    private final StaffId staffId;

    public User(UserId userId, UserName userName, Password password, Language language, RoleId role, StaffId staffId) {
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
        this.userName = Objects.requireNonNull(userName, "userName cannot be null");
        this.password = Objects.requireNonNull(password, "password cannot be null");
        this.language = Objects.requireNonNull(language, "language cannot be null");
        this.roleId = role;
        this.staffId = Objects.requireNonNull(staffId, "branchId cannot be null");
    }

    public static User create(UserId userId, UserName userName, Password password, Language language, RoleId roleId, StaffId staffId) {
        return new User(userId, userName, password, language, roleId, staffId);
    }

    public void changePassword(Password password) {
        this.password = password;
    }

    public void changeLanguage(Language language) {
        if (language == null) {
            throw new IllegalArgumentException("language cannot be null");
        }
        if (language != this.language) {
            this.language = language;
        }
    }

    public void changeRole(RoleId roleId) {
        this.roleId = roleId;
    }

    public UserName getUserName() {
        return userName;
    }

    public UserId getUserId() {
        return userId;
    }

    public RoleId getRoleId() {
        return roleId;
    }

    public Password getPassword() {
        return password;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public Language getLanguage() {
        return language;
    }
}

package com.AjAkrampoor.Academy.shared.dto;

import com.AjAkrampoor.Academy.roles.domain.model.Permission;
import com.AjAkrampoor.Academy.staff.domain.model.StaffStatus;
import com.AjAkrampoor.Academy.users.domain.model.Language;

import java.util.Set;

public class UserResponse {
    private final String userId;
    private final String username;
    private final Language language;
    private final String role;
    private StaffStatus status;
    private final Set<Permission> permissions;

    public UserResponse(String userId,
                        String username, Language language,
                        String role, StaffStatus status,
                        Set<Permission> permissions) {
        this.userId = userId;
        this.username = username;
        this.language = language;
        this.role = role;
        this.status = status;
        this.permissions = permissions;
    }

    public UserResponse(String userId,
                        String username, Language language,
                        String role,
                        Set<Permission> permissions) {
        this.userId = userId;
        this.username = username;
        this.language = language;
        this.role = role;
        this.permissions = permissions;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Language getLanguage() {
        return language;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public StaffStatus getStatus() {
        return status;
    }
}

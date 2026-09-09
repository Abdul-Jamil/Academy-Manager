package com.AjAkrampoor.Academy.roles.application.dto;

import com.AjAkrampoor.Academy.roles.domain.model.Permission;
import com.AjAkrampoor.Academy.roles.domain.model.RoleStatus;

import java.util.Set;

public class RoleResponse {
    private final String id;
    private final String name;
    private final Set<Permission> permissions;
    private final RoleStatus roleStatus;
    private final boolean isDefault;

    public RoleResponse(String id, String name, Set<Permission> permissions, RoleStatus roleStatus, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
        this.roleStatus = roleStatus;
        this.isDefault = isDefault;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public RoleStatus getRoleStatus() {
        return roleStatus;
    }

    public boolean isDefault() {
        return isDefault;
    }
}

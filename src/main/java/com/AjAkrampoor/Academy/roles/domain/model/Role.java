package com.AjAkrampoor.Academy.roles.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Role {

    private final RoleId id;
    private RoleName name;
    private Set<Permission> permissions;
    private RoleStatus roleStatus;
    private boolean isDefault;

    public Role(RoleId id, RoleName name, Set<Permission> permissions, RoleStatus roleStatus, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.permissions = new HashSet<>(permissions);
        this.roleStatus = roleStatus;
        this.isDefault = isDefault;
    }

    public RoleId getId() {
        return id;
    }

    public RoleName getName() {
        return name;
    }

    public RoleStatus getRoleStatus() {
        return roleStatus;
    }

    public boolean isActive() {
        return roleStatus == RoleStatus.ACTIVE;
    }

    public Set<Permission> getPermissions() {
        return Set.copyOf(permissions);
    }


    public void rename(String newName) {
        this.name = new RoleName(newName);
    }

    public void replacePermissions(Set<Permission> newPermissions) {
        this.permissions.clear();
        this.permissions.addAll(newPermissions);
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public void deactivate() {
        if (this.roleStatus == RoleStatus.INACTIVE) {
            return;
        }
        this.roleStatus = RoleStatus.INACTIVE;
    }

    public void reactivate() {
        if (this.roleStatus == RoleStatus.ACTIVE) {
            return;
        }
        this.roleStatus = RoleStatus.ACTIVE;
    }

    public boolean isSuperAdmin() {
        return this.name.getValue().equals("SUPERADMIN");
    }

    public boolean isTeacher() {
        return this.name.getValue().equals("TEACHER");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean isDefault() {
        return isDefault;
    }
}
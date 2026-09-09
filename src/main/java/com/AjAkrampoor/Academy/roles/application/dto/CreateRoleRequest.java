package com.AjAkrampoor.Academy.roles.application.dto;

import com.AjAkrampoor.Academy.roles.domain.model.Permission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class CreateRoleRequest {

    @NotBlank(message = "role name cannot be empty")
    @Size(min = 2, max = 30, message = "role name must be between 2 and 30 characters")
    private String name;
    @NotNull
    private Set<Permission> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}

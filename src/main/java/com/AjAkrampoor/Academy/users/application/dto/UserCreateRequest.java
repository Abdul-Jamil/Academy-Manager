package com.AjAkrampoor.Academy.users.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class UserCreateRequest {

    @NotBlank(message = "First name cannot be empty")
    @Pattern(
            regexp = "^[a-z]+(-[a-z]+)*$",
            message = "Username can only contain lowercase letters and hyphens"
    )
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank
    @Size(min = 5, message = "Password must be at least 5 characters")
    private String password;

    @NotNull
    private UUID roleId;

    @NotBlank
    private String staffId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    
}

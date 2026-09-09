package com.AjAkrampoor.Academy.shared.security;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;
    private final Role role;
    private final BranchId branchId;
    private final Staff staff;

    public CustomUserDetails(User user, Role role, BranchId branchId, Staff staff) {
        this.user = user;
        this.role = role;
        this.branchId = branchId;
        this.staff = staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword().getValue();
    }

    @Override
    public String getUsername() {
        return user.getUserName().getValue();
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public String getUserId() {
        return user.getUserId().toString();
    }

    public BranchId getBranch() {
        return branchId;
    }

    public String getStaffName() {
        return staff.getName().toString();
    }

    public StaffId getStaffId() {
        return staff.getStaffId();
    }

}


package com.AjAkrampoor.Academy.roles.infrastructure.persistence;

import com.AjAkrampoor.Academy.roles.domain.model.Permission;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.model.RoleName;
import com.AjAkrampoor.Academy.roles.domain.model.RoleStatus;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleJpaEntity {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false, unique = true))
    })
    private RoleId id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "role_name", nullable = false, unique = true))
    })
    private RoleName name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role_permissions")
    private Set<Permission> permissions = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "role_status", nullable = false)
    private RoleStatus roleStatus;

    @Column(nullable = false)
    private boolean isDefault;

    public RoleJpaEntity() {
        // For JPA
    }

    public RoleId getId() {
        return id;
    }

    public void setId(RoleId id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public RoleStatus getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(RoleStatus roleStatus) {
        this.roleStatus = roleStatus;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}

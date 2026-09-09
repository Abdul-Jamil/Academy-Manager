package com.AjAkrampoor.Academy.roles.application;

import com.AjAkrampoor.Academy.roles.application.dto.RoleResponse;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleResponseAssembler {

    public RoleResponse toResponse(Role role) {
        return new RoleResponse(
                role.getId().asString(),
                role.getName().getValue(),
                role.getPermissions(),
                role.getRoleStatus(),
                role.isDefault()
        );
    }
}

package com.AjAkrampoor.Academy.roles.presentation.controller;

import com.AjAkrampoor.Academy.roles.application.RoleResponseAssembler;
import com.AjAkrampoor.Academy.roles.application.dto.CreateRoleRequest;
import com.AjAkrampoor.Academy.roles.application.dto.RoleResponse;
import com.AjAkrampoor.Academy.roles.application.usecases.*;
import com.AjAkrampoor.Academy.roles.domain.model.Permission;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleResponseAssembler assembler;
    private final ActivateRoleUseCase activateRole;
    private final ChangeRoleNameUseCase changeRoleName;
    private final CreateRoleUseCase createRole;
    private final DeactivateRoleUseCase deactivateRole;
    private final GetRolesUseCase getRoles;
    private final GetRoleUseCase getRole;
    private final UpdateRolePermissionsUseCase updateRolePermissions;

    public RoleController(RoleResponseAssembler assembler,
                          ActivateRoleUseCase activateRole,
                          ChangeRoleNameUseCase changeRoleName,
                          CreateRoleUseCase createRole,
                          DeactivateRoleUseCase deactivateRole,
                          GetRolesUseCase getRoles,
                          GetRoleUseCase getRole, UpdateRolePermissionsUseCase updateRolePermissions) {
        this.assembler = assembler;
        this.activateRole = activateRole;
        this.changeRoleName = changeRoleName;
        this.createRole = createRole;
        this.deactivateRole = deactivateRole;
        this.getRoles = getRoles;
        this.getRole = getRole;
        this.updateRolePermissions = updateRolePermissions;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<List<RoleResponse>> getAll() {
        List<Role> roles = getRoles.execute();
        List<RoleResponse> response = roles.stream()
                .map(assembler::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<RoleResponse> getOne(@PathVariable String id) {
        Role role = getRole.execute(id);
        return ResponseEntity.ok(assembler.toResponse(role));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody CreateRoleRequest request) {
        Role role = createRole.execute(request.getName(), request.getPermissions());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toResponse(role));
    }

    @PatchMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<RoleResponse> updatePermissions(@PathVariable String id, @RequestParam Set<Permission> permissions) {
        Role role = updateRolePermissions.execute(permissions, id);
        return ResponseEntity.ok(assembler.toResponse(role));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<RoleResponse> rename(@PathVariable String id, @RequestParam String newName) {
        Role role = changeRoleName.execute(id, newName);
        return ResponseEntity.ok(assembler.toResponse(role));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('ROLE_ACTIVATE')")
    public ResponseEntity<RoleResponse> activate(@PathVariable String id) {
        Role role = activateRole.execute(id);
        return ResponseEntity.ok(assembler.toResponse(role));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('ROLE_DEACTIVATE')")
    public ResponseEntity<RoleResponse> deactivate(@PathVariable String id) {
        Role role = deactivateRole.execute(id);
        return ResponseEntity.ok(assembler.toResponse(role));
    }
}
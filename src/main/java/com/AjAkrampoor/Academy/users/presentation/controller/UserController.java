package com.AjAkrampoor.Academy.users.presentation.controller;

import com.AjAkrampoor.Academy.shared.dto.UserResponse;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import com.AjAkrampoor.Academy.users.application.UserResponseAssembler;
import com.AjAkrampoor.Academy.users.application.dto.UserCreateRequest;
import com.AjAkrampoor.Academy.users.application.usecases.*;
import com.AjAkrampoor.Academy.users.domain.model.Language;
import com.AjAkrampoor.Academy.users.domain.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserResponseAssembler assembler;
    private final ChangeUserPasswordUseCase changeUserPassword;
    private final ChangeLanguageUseCase changeLanguage;
    private final CreateUserUseCase createUser;
    private final GetUsersUseCase getUsers;
    private final GetUserUseCase getUser;
    private final GetUserByStaffUseCase getUserByStaff;
    private final UpdateUserRoleUseCase updateUserRole;

    public UserController(UserResponseAssembler assembler,
                          ChangeUserPasswordUseCase changeUserPassword, ChangeLanguageUseCase changeLanguage,
                          CreateUserUseCase createUser,
                          GetUsersUseCase getUsers,
                          GetUserUseCase getUser, GetUserByStaffUseCase getUserByStaff,
                          UpdateUserRoleUseCase updateUserRole) {
        this.assembler = assembler;
        this.changeUserPassword = changeUserPassword;
        this.changeLanguage = changeLanguage;
        this.createUser = createUser;
        this.getUsers = getUsers;
        this.getUser = getUser;
        this.getUserByStaff = getUserByStaff;
        this.updateUserRole = updateUserRole;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<List<UserResponse>> getAll() {
        List<User> all = getUsers.execute();
        List<UserResponse> response = all.stream()
                .map(assembler::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<UserResponse> getOne(@PathVariable UUID id) {
        User user = getUser.execute(String.valueOf(id));
        return ResponseEntity.ok(assembler.toResponse(user));
    }

    @GetMapping("/staff/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<UserResponse> getStaffUser(@PathVariable String id) {
        User user = getUserByStaff.execute(id);
        return ResponseEntity.ok(assembler.toResponse(user));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        User user = createUser.execute(
                request.getUsername(),
                request.getPassword(),
                request.getRoleId(),
                request.getStaffId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toResponse(user));
    }

    @PatchMapping("/{id}/update-role")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserResponse> updateRole(@PathVariable UUID id, @RequestParam UUID roleId) {
        User user = updateUserRole.execute(id, roleId);
        return ResponseEntity.ok(assembler.toResponse(user));
    }

    @PatchMapping("/{id}/change-password")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserResponse> changePassword(@PathVariable UUID id, @RequestParam String password) {
        User user = changeUserPassword.execute(String.valueOf(id), password);
        return ResponseEntity.ok(assembler.toResponse(user));
    }

    @GetMapping("/my-user")
    public ResponseEntity<UserResponse> getOne(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUser.execute(userDetails.getUserId());
        return ResponseEntity.ok(assembler.toResponse(user));
    }

    @PatchMapping("/my-user/change-password")
    public ResponseEntity<UserResponse> changeMyPassword(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam String password) {
        User user = changeUserPassword.execute(userDetails.getUserId(), password);
        return ResponseEntity.ok(assembler.toResponse(user));
    }

    @PatchMapping("/my-user/change-language")
    public ResponseEntity<UserResponse> changeLanguage(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Language language) {
        User user = changeLanguage.execute(userDetails.getUserId(), language);
        return ResponseEntity.ok(assembler.toResponse(user));
    }
}

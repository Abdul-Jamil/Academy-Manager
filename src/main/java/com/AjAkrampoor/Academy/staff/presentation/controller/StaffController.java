package com.AjAkrampoor.Academy.staff.presentation.controller;

import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import com.AjAkrampoor.Academy.staff.application.StaffResponseAssembler;
import com.AjAkrampoor.Academy.staff.application.dto.StaffCreateRequest;
import com.AjAkrampoor.Academy.staff.application.dto.StaffResponse;
import com.AjAkrampoor.Academy.staff.application.dto.StaffUpdateRequest;
import com.AjAkrampoor.Academy.staff.application.usecases.*;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final StaffResponseAssembler assembler;
    private final GetAllStaffUseCase getAllStaff;
    private final GetAllBranchStaffUseCase getAllBranchStaff;
    private final StaffCreateUseCase staffCreate;
    private final StaffUpdateUseCase staffUpdate;
    private final StaffDeactivateUseCase staffDeactivate;
    private final StaffActivateUseCase staffActivate;

    public StaffController(StaffResponseAssembler assembler,
                           GetAllStaffUseCase getAllStaff,
                           GetAllBranchStaffUseCase getAllBranchStaff,
                           StaffCreateUseCase staffCreate,
                           StaffUpdateUseCase staffUpdate,
                           StaffDeactivateUseCase staffDeactivate,
                           StaffActivateUseCase staffActivate) {
        this.assembler = assembler;
        this.getAllStaff = getAllStaff;
        this.getAllBranchStaff = getAllBranchStaff;
        this.staffCreate = staffCreate;
        this.staffUpdate = staffUpdate;
        this.staffDeactivate = staffDeactivate;
        this.staffActivate = staffActivate;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('STAFF_READ')")
    public ResponseEntity<List<StaffResponse>> getAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<StaffResponse> response;

        if (userDetails.getRole().isSuperAdmin()) {
            response = getAllStaff.execute().stream()
                    .map(assembler::toResponse)
                    .toList();
        } else {
            response = getAllBranchStaff.execute(userDetails.getUser().getUserId()).stream()
                    .map(assembler::toResponse)
                    .toList();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('STAFF_CREATE')")
    public ResponseEntity<StaffResponse> create(@Valid @RequestBody StaffCreateRequest request) {
        Staff staff = staffCreate.execute(request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getDescription(),
                request.getBranchId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toResponse(staff));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('STAFF_UPDATE')")
    public ResponseEntity<StaffResponse> update(@PathVariable String id, @RequestBody StaffUpdateRequest request) {
        Staff staff = staffUpdate.execute(id, request.getFirstName(), request.getLastName(), request.getPhoneNumber(), request.getDescription());
        return ResponseEntity.ok(assembler.toResponse(staff));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('STAFF_ACTIVATE')")
    public ResponseEntity<StaffResponse> activate(@PathVariable String id) {
        Staff staff = staffActivate.execute(id);
        return ResponseEntity.ok(assembler.toResponse(staff));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('STAFF_DEACTIVATE')")
    public ResponseEntity<StaffResponse> deactivate(@PathVariable String id) {
        Staff staff = staffDeactivate.execute(id);
        return ResponseEntity.ok(assembler.toResponse(staff));
    }
}

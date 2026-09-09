package com.AjAkrampoor.Academy.inventory.presentation.controller;

import com.AjAkrampoor.Academy.inventory.application.SupplierResponseAssembler;
import com.AjAkrampoor.Academy.inventory.application.dto.SupplierCreateRequest;
import com.AjAkrampoor.Academy.inventory.application.dto.SupplierResponse;
import com.AjAkrampoor.Academy.inventory.application.dto.SupplierUpdateRequest;
import com.AjAkrampoor.Academy.inventory.application.usecases.supplier.*;
import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/suppliers")
public class SupplierController {
    private final GetSuppliersUseCase getSuppliers;
    private final CreateSupplierUseCase createSupplier;
    private final UpdateSupplierUseCase updateSupplier;
    private final ActivateSupplierUseCase activateSupplier;
    private final DeactivateSupplierUseCase deactivateSupplier;
    private final SupplierResponseAssembler assembler;

    public SupplierController(GetSuppliersUseCase getSuppliers,
                              CreateSupplierUseCase createSupplier,
                              UpdateSupplierUseCase updateSupplier,
                              ActivateSupplierUseCase activateSupplier,
                              DeactivateSupplierUseCase deactivateSupplier,
                              SupplierResponseAssembler assembler) {
        this.getSuppliers = getSuppliers;
        this.createSupplier = createSupplier;
        this.updateSupplier = updateSupplier;
        this.activateSupplier = activateSupplier;
        this.deactivateSupplier = deactivateSupplier;
        this.assembler = assembler;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SUPPLIER_READ')")
    public PaginatedResponse<SupplierResponse> getSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "supplierId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Supplier> suppliers = getSuppliers.execute(paginationRequest);
        return suppliers.map(assembler::toResponse);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLIER_CREATE')")
    public SupplierResponse createSupplier(@RequestBody @Valid SupplierCreateRequest request) {
        Supplier supplier = createSupplier.execute(
                request.getName(),
                request.getPhone(),
                request.getAddress(),
                request.getDescription()
        );
        return assembler.toResponse(supplier);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER_UPDATE')")
    public SupplierResponse updateSupplier(@PathVariable UUID id,
                                           @RequestBody @Valid SupplierUpdateRequest request) {
        Supplier supplier = updateSupplier.execute(
                id,
                request.getName(),
                request.getPhone(),
                request.getAddress(),
                request.getDescription()
        );
        return assembler.toResponse(supplier);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('SUPPLIER_ACTIVATE')")
    public SupplierResponse activateSupplier(@PathVariable UUID id) {
        Supplier supplier = activateSupplier.execute(id);
        return assembler.toResponse(supplier);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('SUPPLIER_DEACTIVATE')")
    public SupplierResponse deactivateSupplier(@PathVariable UUID id) {
        Supplier supplier = deactivateSupplier.execute(id);
        return assembler.toResponse(supplier);
    }
}

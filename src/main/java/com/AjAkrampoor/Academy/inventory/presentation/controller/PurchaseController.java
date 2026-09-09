package com.AjAkrampoor.Academy.inventory.presentation.controller;

import com.AjAkrampoor.Academy.inventory.application.PurchaseResponseAssembler;
import com.AjAkrampoor.Academy.inventory.application.dto.PurchaseCreateRequest;
import com.AjAkrampoor.Academy.inventory.application.dto.PurchaseResponse;
import com.AjAkrampoor.Academy.inventory.application.dto.PurchaseUpdateDescriptionRequest;
import com.AjAkrampoor.Academy.inventory.application.usecases.purchase.CancelPurchaseUseCase;
import com.AjAkrampoor.Academy.inventory.application.usecases.purchase.CreatePurchaseUseCase;
import com.AjAkrampoor.Academy.inventory.application.usecases.purchase.GetPurchasesUseCase;
import com.AjAkrampoor.Academy.inventory.application.usecases.purchase.UpdatePurchaseDescriptionUseCase;
import com.AjAkrampoor.Academy.inventory.domain.model.Purchase;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/purchases")
public class PurchaseController {
    private final GetPurchasesUseCase getPurchases;
    private final CreatePurchaseUseCase createPurchase;
    private final UpdatePurchaseDescriptionUseCase updateDescription;
    private final CancelPurchaseUseCase cancelPurchase;
    private final PurchaseResponseAssembler assembler;

    public PurchaseController(GetPurchasesUseCase getPurchases,
                              CreatePurchaseUseCase createPurchase,
                              UpdatePurchaseDescriptionUseCase updateDescription,
                              CancelPurchaseUseCase cancelPurchase,
                              PurchaseResponseAssembler assembler) {
        this.getPurchases = getPurchases;
        this.createPurchase = createPurchase;
        this.updateDescription = updateDescription;
        this.cancelPurchase = cancelPurchase;
        this.assembler = assembler;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PURCHASE_READ')")
    public PaginatedResponse<PurchaseResponse> getPurchases(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "purchaseId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Purchase> purchases = getPurchases.execute(userDetails.getUserId(), paginationRequest);
        return purchases.map(assembler::toResponse);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PURCHASE_CREATE')")
    public PurchaseResponse createPurchase(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @RequestBody @Valid PurchaseCreateRequest request) {
        // Convert request items to the use case's expected record
        List<CreatePurchaseUseCase.PurchaseItemRequest> items = request.getItems().stream()
                .map(item -> new CreatePurchaseUseCase.PurchaseItemRequest(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitCost()
                ))
                .toList();

        Purchase purchase = createPurchase.execute(
                userDetails.getUserId(),
                request.getSupplierId(),
                items,
                request.getDescription(),
                request.getBranchUUID()
        );
        return assembler.toResponse(purchase);
    }

    @PatchMapping("/{id}/description")
    @PreAuthorize("hasAuthority('PURCHASE_UPDATE')")
    public PurchaseResponse updateDescription(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @PathVariable UUID id,
                                              @RequestBody @Valid PurchaseUpdateDescriptionRequest request) {
        Purchase purchase = updateDescription.execute(
                userDetails.getUserId(),
                id,
                request.getDescription()
        );
        return assembler.toResponse(purchase);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('PURCHASE_CANCEL')")
    public PurchaseResponse cancelPurchase(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @PathVariable UUID id) {
        Purchase purchase = cancelPurchase.execute(userDetails.getUserId(), id);
        return assembler.toResponse(purchase);
    }
}

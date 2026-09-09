package com.AjAkrampoor.Academy.inventory.presentation.controller;

import com.AjAkrampoor.Academy.inventory.application.SaleResponseAssembler;
import com.AjAkrampoor.Academy.inventory.application.dto.SaleCreateRequest;
import com.AjAkrampoor.Academy.inventory.application.dto.SaleResponse;
import com.AjAkrampoor.Academy.inventory.application.dto.SaleUpdateDescriptionRequest;
import com.AjAkrampoor.Academy.inventory.application.usecases.sale.CancelSaleUseCase;
import com.AjAkrampoor.Academy.inventory.application.usecases.sale.CreateSaleUseCase;
import com.AjAkrampoor.Academy.inventory.application.usecases.sale.GetSalesUseCase;
import com.AjAkrampoor.Academy.inventory.application.usecases.sale.UpdateSaleDescriptionUseCase;
import com.AjAkrampoor.Academy.inventory.domain.model.Sale;
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
@RequestMapping("/api/inventory/sales")
public class SaleController {
    private final GetSalesUseCase getSales;
    private final CreateSaleUseCase createSale;
    private final UpdateSaleDescriptionUseCase updateDescription;
    private final CancelSaleUseCase cancelSale;
    private final SaleResponseAssembler assembler;

    public SaleController(GetSalesUseCase getSales,
                          CreateSaleUseCase createSale,
                          UpdateSaleDescriptionUseCase updateDescription,
                          CancelSaleUseCase cancelSale,
                          SaleResponseAssembler assembler) {
        this.getSales = getSales;
        this.createSale = createSale;
        this.updateDescription = updateDescription;
        this.cancelSale = cancelSale;
        this.assembler = assembler;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SALE_READ')")
    public PaginatedResponse<SaleResponse> getSales(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "saleId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Sale> sales = getSales.execute(userDetails.getUserId(), paginationRequest);
        return sales.map(assembler::toResponse);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SALE_CREATE')")
    public SaleResponse createSale(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestBody @Valid SaleCreateRequest request) {
        // Convert request items to the use case's expected record
        List<CreateSaleUseCase.SaleItemRequest> items = request.getItems().stream()
                .map(item -> new CreateSaleUseCase.SaleItemRequest(
                        item.getProductId(),
                        item.getQuantity()
                ))
                .toList();

        Sale sale = createSale.execute(
                userDetails.getUserId(),
                items,
                request.getDiscountAmount(),
                request.getDescription(),
                request.getBranchUUID()
        );
        return assembler.toResponse(sale);
    }

    @PatchMapping("/{id}/description")
    @PreAuthorize("hasAuthority('SALE_UPDATE')")
    public SaleResponse updateDescription(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable UUID id,
                                          @RequestBody @Valid SaleUpdateDescriptionRequest request) {
        Sale sale = updateDescription.execute(
                userDetails.getUserId(),
                id,
                request.getDescription()
        );
        return assembler.toResponse(sale);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('SALE_CANCEL')")
    public SaleResponse cancelSale(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable UUID id) {
        Sale sale = cancelSale.execute(userDetails.getUserId(), id);
        return assembler.toResponse(sale);
    }
}

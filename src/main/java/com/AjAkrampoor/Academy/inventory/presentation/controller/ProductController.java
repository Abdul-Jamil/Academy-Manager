package com.AjAkrampoor.Academy.inventory.presentation.controller;

import com.AjAkrampoor.Academy.inventory.application.ProductResponseAssembler;
import com.AjAkrampoor.Academy.inventory.application.dto.ProductCreateRequest;
import com.AjAkrampoor.Academy.inventory.application.dto.ProductResponse;
import com.AjAkrampoor.Academy.inventory.application.dto.ProductUpdateRequest;
import com.AjAkrampoor.Academy.inventory.application.usecases.product.*;
import com.AjAkrampoor.Academy.inventory.domain.model.Product;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/products")
public class ProductController {
    private final GetProductsUseCase getProducts;
    private final CreateProductUseCase createProduct;
    private final UpdateProductUseCase updateProduct;
    private final ActivateProductUseCase activateProduct;
    private final DeactivateProductUseCase deactivateProduct;
    private final ProductResponseAssembler assembler;

    public ProductController(GetProductsUseCase getProducts,
                             CreateProductUseCase createProduct,
                             UpdateProductUseCase updateProduct,
                             ActivateProductUseCase activateProduct,
                             DeactivateProductUseCase deactivateProduct,
                             ProductResponseAssembler assembler) {
        this.getProducts = getProducts;
        this.createProduct = createProduct;
        this.updateProduct = updateProduct;
        this.activateProduct = activateProduct;
        this.deactivateProduct = deactivateProduct;
        this.assembler = assembler;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public PaginatedResponse<ProductResponse> getProducts(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "productId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Product> products = getProducts.execute(userDetails.getUserId(), paginationRequest);
        return products.map(assembler::toResponse);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    public ProductResponse createProduct(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @RequestBody @Valid ProductCreateRequest request) {
        Product product = createProduct.execute(
                userDetails.getUserId(),
                request.getProductName(),
                request.getDescription(),
                request.getSellingPrice(),
                request.getQuantity(),
                request.getCategoryId(),
                request.getBranchUUID()
        );
        return assembler.toResponse(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ProductResponse updateProduct(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @PathVariable UUID id,
                                         @RequestBody @Valid ProductUpdateRequest request) {
        Product product = updateProduct.execute(
                userDetails.getUserId(),
                id,
                request.getProductName(),
                request.getDescription(),
                request.getSellingPrice(),
                request.getCategoryId()
        );
        return assembler.toResponse(product);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('PRODUCT_ACTIVATE')")
    public ProductResponse activateProduct(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @PathVariable UUID id) {
        Product product = activateProduct.execute(userDetails.getUserId(), id);
        return assembler.toResponse(product);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('PRODUCT_DEACTIVATE')")
    public ProductResponse deactivateProduct(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable UUID id) {
        Product product = deactivateProduct.execute(userDetails.getUserId(), id);
        return assembler.toResponse(product);
    }
}

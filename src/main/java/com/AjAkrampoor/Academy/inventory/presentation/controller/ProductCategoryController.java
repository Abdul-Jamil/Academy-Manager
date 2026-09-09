package com.AjAkrampoor.Academy.inventory.presentation.controller;

import com.AjAkrampoor.Academy.inventory.application.ProductCategoryResponseAssembler;
import com.AjAkrampoor.Academy.inventory.application.dto.ProductCategoryCreateRequest;
import com.AjAkrampoor.Academy.inventory.application.dto.ProductCategoryResponse;
import com.AjAkrampoor.Academy.inventory.application.dto.ProductCategoryUpdateRequest;
import com.AjAkrampoor.Academy.inventory.application.usecases.category.*;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/categories")
public class ProductCategoryController {
    private final GetProductCategoriesUseCase getCategories;
    private final CreateProductCategoryUseCase createCategory;
    private final UpdateProductCategoryUseCase updateCategory;
    private final ActivateProductCategoryUseCase activateCategory;
    private final DeactivateProductCategoryUseCase deactivateCategory;
    private final ProductCategoryResponseAssembler assembler;

    public ProductCategoryController(GetProductCategoriesUseCase getCategories,
                                     CreateProductCategoryUseCase createCategory,
                                     UpdateProductCategoryUseCase updateCategory,
                                     ActivateProductCategoryUseCase activateCategory,
                                     DeactivateProductCategoryUseCase deactivateCategory,
                                     ProductCategoryResponseAssembler assembler) {
        this.getCategories = getCategories;
        this.createCategory = createCategory;
        this.updateCategory = updateCategory;
        this.activateCategory = activateCategory;
        this.deactivateCategory = deactivateCategory;
        this.assembler = assembler;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_CATEGORY_READ')")
    public List<ProductCategoryResponse> getCategories() {
        List<ProductCategory> categories = getCategories.execute();
        return categories.stream()
                .map(assembler::toResponse)
                .toList();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_CATEGORY_CREATE')")
    public ProductCategoryResponse createCategory(@RequestBody @Valid ProductCategoryCreateRequest request) {
        ProductCategory category = createCategory.execute(request.getName(), request.getDescription());
        return assembler.toResponse(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_CATEGORY_UPDATE')")
    public ProductCategoryResponse updateCategory(@PathVariable UUID id,
                                                  @RequestBody @Valid ProductCategoryUpdateRequest request) {
        ProductCategory category = updateCategory.execute(id, request.getName(), request.getDescription());
        return assembler.toResponse(category);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('PRODUCT_CATEGORY_ACTIVATE')")
    public ProductCategoryResponse activateCategory(@PathVariable UUID id) {
        ProductCategory category = activateCategory.execute(id);
        return assembler.toResponse(category);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('PRODUCT_CATEGORY_DEACTIVATE')")
    public ProductCategoryResponse deactivateCategory(@PathVariable UUID id) {
        ProductCategory category = deactivateCategory.execute(id);
        return assembler.toResponse(category);
    }
}

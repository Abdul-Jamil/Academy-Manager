package com.AjAkrampoor.Academy.income.presentation.controller;

import com.AjAkrampoor.Academy.income.application.IncomeCategoryResponseAssembler;
import com.AjAkrampoor.Academy.income.application.IncomeResponseAssembler;
import com.AjAkrampoor.Academy.income.application.dto.IncomeCategoryResponse;
import com.AjAkrampoor.Academy.income.application.dto.IncomeCreateRequest;
import com.AjAkrampoor.Academy.income.application.dto.IncomeResponse;
import com.AjAkrampoor.Academy.income.application.usecases.*;
import com.AjAkrampoor.Academy.income.domain.model.Profit;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final GetIncomesUseCase getIncomes;
    private final CreateIncomeUseCase createIncome;
    private final UpdateIncomeUseCase updateIncome;
    private final DeactivateIncomeUseCase deactivateIncome;
    private final ActivateIncomeUseCase activateIncome;

    private final GetIncomeCategoriesUseCase getIncomeCategories;
    private final CreateIncomeCategoryUseCase createIncomeCategory;
    private final UpdateIncomeCategoryUseCase updateIncomeCategory;
    private final DeactivateIncomeCategoryUseCase deactivateIncomeCategory;
    private final ActivateIncomeCategoryUseCase activateIncomeCategory;

    private final IncomeCategoryResponseAssembler categoryResponseAssembler;
    private final IncomeResponseAssembler incomeResponseAssembler;

    public IncomeController(GetIncomesUseCase getIncomes, CreateIncomeUseCase createIncome,
                            UpdateIncomeUseCase updateIncome, DeactivateIncomeUseCase deactivateIncome,
                            ActivateIncomeUseCase activateIncome,
                            GetIncomeCategoriesUseCase getIncomeCategories,
                            CreateIncomeCategoryUseCase createIncomeCategory,
                            UpdateIncomeCategoryUseCase updateIncomeCategory,
                            DeactivateIncomeCategoryUseCase deactivateIncomeCategory,
                            ActivateIncomeCategoryUseCase activateIncomeCategory,
                            IncomeCategoryResponseAssembler categoryResponseAssembler,
                            IncomeResponseAssembler incomeResponseAssembler) {
        this.getIncomes = getIncomes;
        this.createIncome = createIncome;
        this.updateIncome = updateIncome;
        this.deactivateIncome = deactivateIncome;
        this.activateIncome = activateIncome;
        this.getIncomeCategories = getIncomeCategories;
        this.createIncomeCategory = createIncomeCategory;
        this.updateIncomeCategory = updateIncomeCategory;
        this.deactivateIncomeCategory = deactivateIncomeCategory;
        this.activateIncomeCategory = activateIncomeCategory;
        this.categoryResponseAssembler = categoryResponseAssembler;
        this.incomeResponseAssembler = incomeResponseAssembler;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('INCOME_READ')")
    public PaginatedResponse<IncomeResponse> getIncomes(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "profitId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Profit> profits = getIncomes.execute(userDetails.getUserId(), paginationRequest);
        return profits.map(incomeResponseAssembler::toResponse);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INCOME_CREATE')")
    public IncomeResponse createIncome(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestBody @Valid IncomeCreateRequest request) {
        Profit profit = createIncome.execute(userDetails.getUserId(), request.getAmount(),
                request.getCategoryId(), request.getBranchUUID(), request.getDescription());
        return incomeResponseAssembler.toResponse(profit);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('INCOME_UPDATE')")
    public IncomeResponse updateIncome(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @PathVariable UUID id,
                                       @RequestParam(required = false) String description,
                                       @RequestParam(required = false) UUID categoryId) {
        if (description == null && categoryId == null) {
            throw new IllegalArgumentException("Nothing to update");
        }
        Profit profit = updateIncome.execute(userDetails.getUserId(), id, JsonNullable.of(description), categoryId);
        return incomeResponseAssembler.toResponse(profit);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('INCOME_DELETE')") // or INCOME_DEACTIVATE
    public IncomeResponse deactivateIncome(@PathVariable UUID id,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        Profit profit = deactivateIncome.execute(userDetails.getUserId(), id);
        return incomeResponseAssembler.toResponse(profit);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('INCOME_ACTIVATE')")
    public IncomeResponse activateIncome(@PathVariable UUID id,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        Profit profit = activateIncome.execute(userDetails.getUserId(), id);
        return incomeResponseAssembler.toResponse(profit);
    }

    @GetMapping("/category")
    @PreAuthorize("hasAuthority('INCOME_CATEGORY_READ')")
    public List<IncomeCategoryResponse> getIncomeCategories() {
        List<ProfitCategory> categories = getIncomeCategories.execute();
        return categories.stream()
                .map(categoryResponseAssembler::toResponse)
                .toList();
    }

    @PostMapping("/category")
    @PreAuthorize("hasAuthority('INCOME_CATEGORY_CREATE')")
    public IncomeCategoryResponse createCategory(@RequestParam String name,
                                                 @RequestParam(required = false) String description) {
        ProfitCategory category = createIncomeCategory.execute(name, description);
        return categoryResponseAssembler.toResponse(category);
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasAuthority('INCOME_CATEGORY_UPDATE')")
    public IncomeCategoryResponse updateCategory(@PathVariable UUID id,
                                                 @RequestParam String name,
                                                 @RequestParam(required = false) String description) {
        ProfitCategory category = updateIncomeCategory.execute(id, name, description);
        return categoryResponseAssembler.toResponse(category);
    }

    @PatchMapping("/category/{id}/activate")
    @PreAuthorize("hasAuthority('INCOME_CATEGORY_ACTIVATE')")
    public IncomeCategoryResponse activateCategory(@PathVariable UUID id) {
        ProfitCategory category = activateIncomeCategory.execute(id);
        return categoryResponseAssembler.toResponse(category);
    }

    @PatchMapping("/category/{id}/deactivate")
    @PreAuthorize("hasAuthority('INCOME_CATEGORY_DEACTIVATE')")
    public IncomeCategoryResponse deactivateCategory(@PathVariable UUID id) {
        ProfitCategory category = deactivateIncomeCategory.execute(id);
        return categoryResponseAssembler.toResponse(category);
    }
}

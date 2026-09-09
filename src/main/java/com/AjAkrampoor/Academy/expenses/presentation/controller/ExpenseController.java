package com.AjAkrampoor.Academy.expenses.presentation.controller;

import com.AjAkrampoor.Academy.expenses.application.ExpenseCategoryResponseAssembler;
import com.AjAkrampoor.Academy.expenses.application.ExpenseResponseAssembler;
import com.AjAkrampoor.Academy.expenses.application.dto.ExpenseCategoryResponse;
import com.AjAkrampoor.Academy.expenses.application.dto.ExpenseCreateRequest;
import com.AjAkrampoor.Academy.expenses.application.dto.ExpenseResponse;
import com.AjAkrampoor.Academy.expenses.application.usecases.*;
import com.AjAkrampoor.Academy.expenses.domain.model.Expense;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
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
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final GetExpensesUseCase getExpenses;
    private final CreateExpenseUseCase createExpense;
    private final UpdateExpenseUseCase updateExpense;
    private final DeleteExpenseUseCase deleteExpense;

    private final GetExpenseCategoriesUseCase getExpenseCategories;
    private final CreateExpenseCategoryUseCase createExpenseCategory;
    private final UpdateExpenseCategoryUseCase updateExpenseCategory;
    private final DeactivateExpenseCategoryUseCase deactivateExpenseCategory;
    private final ActivateExpenseCategoryUseCase activateExpenseCategory;

    private final ExpenseCategoryResponseAssembler categoryResponseAssembler;
    private final ExpenseResponseAssembler expenseResponseAssembler;


    public ExpenseController(GetExpensesUseCase getExpenses, CreateExpenseUseCase createExpense, UpdateExpenseUseCase updateExpense, DeleteExpenseUseCase deleteExpense, GetExpenseCategoriesUseCase getExpenseCategories, CreateExpenseCategoryUseCase createExpenseCategory, UpdateExpenseCategoryUseCase updateExpenseCategory, DeactivateExpenseCategoryUseCase deactivateExpenseCategory, ActivateExpenseCategoryUseCase activateExpenseCategory, ExpenseCategoryResponseAssembler categoryResponseAssembler, ExpenseResponseAssembler expenseResponseAssembler) {
        this.getExpenses = getExpenses;
        this.createExpense = createExpense;
        this.updateExpense = updateExpense;
        this.deleteExpense = deleteExpense;
        this.getExpenseCategories = getExpenseCategories;
        this.createExpenseCategory = createExpenseCategory;
        this.updateExpenseCategory = updateExpenseCategory;
        this.deactivateExpenseCategory = deactivateExpenseCategory;
        this.activateExpenseCategory = activateExpenseCategory;
        this.categoryResponseAssembler = categoryResponseAssembler;
        this.expenseResponseAssembler = expenseResponseAssembler;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('EXPENSE_READ')")
    public PaginatedResponse<ExpenseResponse> getExpenses(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Expense> expenses = getExpenses.execute(userDetails.getUserId(), paginationRequest);

        return expenses.map(expenseResponseAssembler::toResponse);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('EXPENSE_CREATE')")
    public ExpenseResponse createExpense(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid ExpenseCreateRequest request) {
        Expense expense = createExpense.execute(userDetails.getUserId(), request.getAmount(), request.getCategoryId(), request.getBranchUUID(), request.getDescription());
        return expenseResponseAssembler.toResponse(expense);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EXPENSE_UPDATE')")
    public ExpenseResponse updateExpense(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id, @RequestParam(required = false) String description, @RequestParam(required = false) UUID categoryId) {
        if (description == null && categoryId == null) {
            throw new IllegalArgumentException("Nothing to update");
        }
        Expense expense = updateExpense.execute(userDetails.getUserId(), id, JsonNullable.of(description), categoryId);
        return expenseResponseAssembler.toResponse(expense);
    }

    @PatchMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('EXPENSE_DELETE')")
    public ExpenseResponse deleteExpense(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Expense expense = deleteExpense.execute(userDetails.getUserId(), id);
        return expenseResponseAssembler.toResponse(expense);
    }

    @GetMapping("/category")
    @PreAuthorize("hasAuthority('EXPENSE_CATEGORY_READ')")
    public List<ExpenseCategoryResponse> getExpenseCategories() {
        List<ExpenseCategory> categories = getExpenseCategories.execute();
        return categories.stream()
                .map(categoryResponseAssembler::toResponse)
                .toList();
    }

    @PostMapping("/category")
    @PreAuthorize("hasAuthority('EXPENSE_CATEGORY_CREATE')")
    public ExpenseCategoryResponse createCategory(@RequestParam String name, @RequestParam(required = false) String description) {
        ExpenseCategory category = createExpenseCategory.execute(name, description);
        return categoryResponseAssembler.toResponse(category);
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasAuthority('EXPENSE_CATEGORY_UPDATE')")
    public ExpenseCategoryResponse updateCategory(@PathVariable UUID id, @RequestParam String name, @RequestParam(required = false) String description) {
        ExpenseCategory category = updateExpenseCategory.execute(id, name, description);
        return categoryResponseAssembler.toResponse(category);
    }

    @PatchMapping("/category/{id}/activate")
    @PreAuthorize("hasAuthority('EXPENSE_CATEGORY_ACTIVATE')")
    public ExpenseCategoryResponse activateCategory(@PathVariable UUID id) {
        ExpenseCategory category = activateExpenseCategory.execute(id);
        return categoryResponseAssembler.toResponse(category);
    }

    @PatchMapping("/category/{id}/deactivate")
    @PreAuthorize("hasAuthority('EXPENSE_CATEGORY_DEACTIVATE')")
    public ExpenseCategoryResponse deactivateCategory(@PathVariable UUID id) {
        ExpenseCategory category = deactivateExpenseCategory.execute(id);
        return categoryResponseAssembler.toResponse(category);
    }
}

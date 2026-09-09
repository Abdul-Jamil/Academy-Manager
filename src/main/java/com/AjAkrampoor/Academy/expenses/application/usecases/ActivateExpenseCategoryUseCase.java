package com.AjAkrampoor.Academy.expenses.application.usecases;

import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryId;
import com.AjAkrampoor.Academy.expenses.domain.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ActivateExpenseCategoryUseCase {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ActivateExpenseCategoryUseCase(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Transactional
    public ExpenseCategory execute(UUID categoryId) {
        ExpenseCategory category = expenseCategoryRepository.findById(new ExpenseCategoryId(categoryId)).orElseThrow(() -> new IllegalArgumentException("No such category found"));

        if (category.isActive()) {
            return category;
        }

        category.activate();
        return expenseCategoryRepository.save(category);
    }
}

package com.AjAkrampoor.Academy.expenses.application.usecases;

import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
import com.AjAkrampoor.Academy.expenses.domain.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetExpenseCategoriesUseCase {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public GetExpenseCategoriesUseCase(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public List<ExpenseCategory> execute() {
        return expenseCategoryRepository.findAll();
    }
}

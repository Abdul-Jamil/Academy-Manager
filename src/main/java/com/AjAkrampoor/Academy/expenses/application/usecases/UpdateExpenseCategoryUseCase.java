package com.AjAkrampoor.Academy.expenses.application.usecases;

import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryName;
import com.AjAkrampoor.Academy.expenses.domain.repository.ExpenseCategoryRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateExpenseCategoryUseCase {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public UpdateExpenseCategoryUseCase(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Transactional
    public ExpenseCategory execute(UUID expenseCategoryUUID, String newNameStr, String descriptionStr) {
        ExpenseCategory category = expenseCategoryRepository.findById(new ExpenseCategoryId(expenseCategoryUUID)).orElseThrow(() -> new IllegalArgumentException("No such expenseCategory found"));

        if (!category.isActive()) {
            throw new IllegalStateException("Expense category is not active.");
        }

        ExpenseCategoryName name = new ExpenseCategoryName(newNameStr);

        if (expenseCategoryRepository.existsByName(name)) {
            throw new IllegalStateException("Expense category already exists.");
        }

        Description description = (descriptionStr == null) ? null : new Description(descriptionStr);
        category.updateName(name);
        category.updateDescription(description);

        return expenseCategoryRepository.save(category);

    }
}

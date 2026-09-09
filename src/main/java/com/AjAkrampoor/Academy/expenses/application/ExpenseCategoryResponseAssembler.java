package com.AjAkrampoor.Academy.expenses.application;

import com.AjAkrampoor.Academy.expenses.application.dto.ExpenseCategoryResponse;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCategoryResponseAssembler {
    public ExpenseCategoryResponse toResponse(ExpenseCategory expenseCategory) {

        String description = (expenseCategory.getDescription() != null) ? expenseCategory.getDescription().getValue() : null;

        return new ExpenseCategoryResponse
                (
                        expenseCategory.getCategoryId().toString(),
                        expenseCategory.getCategoryName().getValue(),
                        description,
                        expenseCategory.getCategoryStatus()
                );
    }
}

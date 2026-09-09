package com.AjAkrampoor.Academy.expenses.domain.repository;

import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryName;

import java.util.List;
import java.util.Optional;

public interface ExpenseCategoryRepository {
    boolean existsByName(ExpenseCategoryName name);

    boolean existsById(ExpenseCategoryId id);

    ExpenseCategory save(ExpenseCategory expenseCategory);

    Optional<ExpenseCategory> findById(ExpenseCategoryId id);

    List<ExpenseCategory> findAll();
}

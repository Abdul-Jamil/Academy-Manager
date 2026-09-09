package com.AjAkrampoor.Academy.expenses.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.expenses.domain.model.Expense;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRepository {

    boolean existsById(ExpenseId id);

    Optional<Expense> findById(ExpenseId id);

    Expense save(Expense expense);

    Page<Expense> findAll(Pageable pageable);

    Page<Expense> findAllByBranchId(BranchId branchId, Pageable pageable);
}

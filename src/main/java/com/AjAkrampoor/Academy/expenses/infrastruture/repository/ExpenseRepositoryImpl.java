package com.AjAkrampoor.Academy.expenses.infrastruture.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.expenses.domain.model.Expense;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseId;
import com.AjAkrampoor.Academy.expenses.domain.repository.ExpenseRepository;
import com.AjAkrampoor.Academy.expenses.infrastruture.persistence.ExpenseJpaEntity;
import com.AjAkrampoor.Academy.expenses.presentation.mapper.ExpenseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final ExpenseJpaRepository repository;

    public ExpenseRepositoryImpl(ExpenseJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(ExpenseId id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<Expense> findById(ExpenseId id) {
        return repository.findById(id).map(ExpenseMapper::toDomain);
    }

    @Override
    public Expense save(Expense expense) {
        ExpenseJpaEntity saved = repository.save(ExpenseMapper.toEntity(expense));
        return ExpenseMapper.toDomain(saved);
    }

    @Override
    public Page<Expense> findAll(Pageable pageable) {
        Page<ExpenseJpaEntity> entities = repository.findAll(pageable);
        return entities.map(ExpenseMapper::toDomain);
    }

    @Override
    public Page<Expense> findAllByBranchId(BranchId branchId, Pageable pageable) {
        Page<ExpenseJpaEntity> entities = repository.findAllByBranchId(branchId, pageable);
        return entities.map(ExpenseMapper::toDomain);
    }
}

package com.AjAkrampoor.Academy.expenses.infrastruture.repository;

import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryName;
import com.AjAkrampoor.Academy.expenses.domain.repository.ExpenseCategoryRepository;
import com.AjAkrampoor.Academy.expenses.infrastruture.persistence.ExpenseCategoryJpaEntity;
import com.AjAkrampoor.Academy.expenses.presentation.mapper.ExpenseCategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExpenseCategoryRepositoryImpl implements ExpenseCategoryRepository {
    private final ExpenseCategoryJpaRepository repository;

    public ExpenseCategoryRepositoryImpl(ExpenseCategoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsByName(ExpenseCategoryName name) {
        return repository.existsByName(name);
    }

    @Override
    public boolean existsById(ExpenseCategoryId id) {
        return repository.existsById(id);
    }

    @Override
    public ExpenseCategory save(ExpenseCategory expenseCategory) {
        ExpenseCategoryJpaEntity saved = repository.save(ExpenseCategoryMapper.toEntity(expenseCategory));
        return ExpenseCategoryMapper.toDomain(saved);
    }

    @Override
    public Optional<ExpenseCategory> findById(ExpenseCategoryId id) {
        Optional<ExpenseCategoryJpaEntity> entity = repository.findById(id);
        return entity.map(ExpenseCategoryMapper::toDomain);
    }

    @Override
    public List<ExpenseCategory> findAll() {
        List<ExpenseCategoryJpaEntity> entities = repository.findAll();
        return ExpenseCategoryMapper.toDomain(entities);
    }
}

package com.AjAkrampoor.Academy.income.infrastructure.repository;

import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryName;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitCategoryRepository;
import com.AjAkrampoor.Academy.income.infrastructure.persistence.ProfitCategoryJpaEntity;
import com.AjAkrampoor.Academy.income.presentation.mapper.ProfitCategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfitCategoryRepositoryImpl implements ProfitCategoryRepository {
    private final ProfitCategoryJpaRepository repository;

    public ProfitCategoryRepositoryImpl(ProfitCategoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsByName(ProfitCategoryName name) {
        return repository.existsByCategoryName(name);
    }

    @Override
    public boolean existsById(ProfitCategoryId id) {
        return repository.existsById(id);
    }

    @Override
    public ProfitCategory save(ProfitCategory category) {
        ProfitCategoryJpaEntity saved = repository.save(ProfitCategoryMapper.toEntity(category));
        return ProfitCategoryMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfitCategory> findById(ProfitCategoryId id) {
        return repository.findById(id).map(ProfitCategoryMapper::toDomain);
    }

    @Override
    public List<ProfitCategory> findAll() {
        return ProfitCategoryMapper.toDomain(repository.findAll());
    }
}

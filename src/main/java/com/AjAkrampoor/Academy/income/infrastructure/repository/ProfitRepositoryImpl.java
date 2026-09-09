package com.AjAkrampoor.Academy.income.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.income.domain.model.Profit;
import com.AjAkrampoor.Academy.income.domain.model.ProfitId;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitRepository;
import com.AjAkrampoor.Academy.income.infrastructure.persistence.ProfitJpaEntity;
import com.AjAkrampoor.Academy.income.presentation.mapper.ProfitMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProfitRepositoryImpl implements ProfitRepository {
    private final ProfitJpaRepository repository;

    public ProfitRepositoryImpl(ProfitJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(ProfitId id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<Profit> findById(ProfitId id) {
        return repository.findById(id).map(ProfitMapper::toDomain);
    }

    @Override
    public Profit save(Profit profit) {
        ProfitJpaEntity saved = repository.save(ProfitMapper.toEntity(profit));
        return ProfitMapper.toDomain(saved);
    }

    @Override
    public Page<Profit> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(ProfitMapper::toDomain);
    }

    @Override
    public Page<Profit> findAllByBranchId(BranchId branchId, Pageable pageable) {
        return repository.findAllByBranchId(branchId, pageable).map(ProfitMapper::toDomain);
    }
}

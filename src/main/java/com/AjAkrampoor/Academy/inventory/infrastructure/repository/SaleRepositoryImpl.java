package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.Sale;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleId;
import com.AjAkrampoor.Academy.inventory.domain.repository.SaleRepository;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.SaleJpaEntity;
import com.AjAkrampoor.Academy.inventory.presentation.mapper.SaleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SaleRepositoryImpl implements SaleRepository {

    private final SaleJpaRepository jpaRepository;

    public SaleRepositoryImpl(SaleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsById(SaleId id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public Optional<Sale> findById(SaleId id) {
        return jpaRepository.findById(id).map(SaleMapper::toDomain);
    }

    @Override
    public Sale save(Sale sale) {
        SaleJpaEntity entity = SaleMapper.toEntity(sale);
        SaleJpaEntity saved = jpaRepository.save(entity);
        return SaleMapper.toDomain(saved);
    }

    @Override
    public Page<Sale> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable).map(SaleMapper::toDomain);
    }

    @Override
    public Page<Sale> findAllByBranchId(BranchId branchId, Pageable pageable) {
        return jpaRepository.findAllByBranchId(branchId, pageable).map(SaleMapper::toDomain);
    }
}

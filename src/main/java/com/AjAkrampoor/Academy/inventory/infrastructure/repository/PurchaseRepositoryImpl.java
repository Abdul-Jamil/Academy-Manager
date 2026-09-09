package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.Purchase;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import com.AjAkrampoor.Academy.inventory.domain.repository.PurchaseRepository;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.PurchaseJpaEntity;
import com.AjAkrampoor.Academy.inventory.presentation.mapper.PurchaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {

    private final PurchaseJpaRepository jpaRepository;

    public PurchaseRepositoryImpl(PurchaseJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsById(PurchaseId id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public Optional<Purchase> findById(PurchaseId id) {
        return jpaRepository.findById(id).map(PurchaseMapper::toDomain);
    }

    @Override
    public Purchase save(Purchase purchase) {
        PurchaseJpaEntity entity = PurchaseMapper.toEntity(purchase);
        PurchaseJpaEntity saved = jpaRepository.save(entity);
        return PurchaseMapper.toDomain(saved);
    }

    @Override
    public Page<Purchase> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable).map(PurchaseMapper::toDomain);
    }

    @Override
    public Page<Purchase> findAllByBranchId(BranchId branchId, Pageable pageable) {
        return jpaRepository.findAllByBranchId(branchId, pageable).map(PurchaseMapper::toDomain);
    }
}

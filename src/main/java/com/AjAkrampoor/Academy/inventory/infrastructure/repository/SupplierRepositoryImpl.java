package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierId;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierName;
import com.AjAkrampoor.Academy.inventory.domain.repository.SupplierRepository;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.SupplierJpaEntity;
import com.AjAkrampoor.Academy.inventory.presentation.mapper.SupplierMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    private final SupplierJpaRepository jpaRepository;

    public SupplierRepositoryImpl(SupplierJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsById(SupplierId id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByName(SupplierName name) {
        return jpaRepository.existsBySupplierName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(SupplierName name, SupplierId id) {
        return jpaRepository.existsBySupplierNameAndSupplierIdNot(name, id);
    }

    @Override
    public Optional<Supplier> findById(SupplierId id) {
        return jpaRepository.findById(id).map(SupplierMapper::toDomain);
    }

    @Override
    public Supplier save(Supplier supplier) {
        SupplierJpaEntity entity = SupplierMapper.toEntity(supplier);
        SupplierJpaEntity saved = jpaRepository.save(entity);
        return SupplierMapper.toDomain(saved);
    }

    @Override
    public Page<Supplier> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable).map(SupplierMapper::toDomain);
    }
}

package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.Product;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductName;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductRepository;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.ProductJpaEntity;
import com.AjAkrampoor.Academy.inventory.presentation.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsById(ProductId id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByProductNameAndBranchId(ProductName productName, BranchId branchId) {
        return jpaRepository.existsByProductNameAndBranchId(productName, branchId);
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return jpaRepository.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = ProductMapper.toEntity(product);
        ProductJpaEntity saved = jpaRepository.save(entity);
        return ProductMapper.toDomain(saved);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable).map(ProductMapper::toDomain);
    }

    @Override
    public Page<Product> findAllByBranchId(BranchId branchId, Pageable pageable) {
        return jpaRepository.findAllByBranchId(branchId, pageable).map(ProductMapper::toDomain);
    }

    @Override
    public boolean existsByProductNameAndBranchIdAndProductIdNot(ProductName productName, BranchId branchId, ProductId productId) {
        return jpaRepository.existsByProductNameAndBranchIdAndProductIdNot(productName, branchId, productId);
    }
}

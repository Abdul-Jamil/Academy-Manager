package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryName;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductCategoryRepository;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.ProductCategoryJpaEntity;
import com.AjAkrampoor.Academy.inventory.presentation.mapper.ProductCategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    private final ProductCategoryJpaRepository jpaRepository;

    public ProductCategoryRepositoryImpl(ProductCategoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsByName(ProductCategoryName name) {
        return jpaRepository.existsByCategoryName(name);
    }

    @Override
    public boolean existsById(ProductCategoryId id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public ProductCategory save(ProductCategory category) {
        ProductCategoryJpaEntity entity = ProductCategoryMapper.toEntity(category);
        ProductCategoryJpaEntity saved = jpaRepository.save(entity);
        return ProductCategoryMapper.toDomain(saved);
    }

    @Override
    public Optional<ProductCategory> findById(ProductCategoryId id) {
        return jpaRepository.findById(id).map(ProductCategoryMapper::toDomain);
    }

    @Override
    public List<ProductCategory> findAll() {
        return ProductCategoryMapper.toDomainList(jpaRepository.findAll());
    }
}

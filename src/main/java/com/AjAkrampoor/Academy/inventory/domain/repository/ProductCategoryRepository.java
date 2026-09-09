package com.AjAkrampoor.Academy.inventory.domain.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryName;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository {
    boolean existsByName(ProductCategoryName name);

    boolean existsById(ProductCategoryId id);

    ProductCategory save(ProductCategory category);

    Optional<ProductCategory> findById(ProductCategoryId id);

    List<ProductCategory> findAll();
}

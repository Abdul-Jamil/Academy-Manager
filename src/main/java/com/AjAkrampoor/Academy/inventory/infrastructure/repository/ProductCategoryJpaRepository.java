package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryName;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.ProductCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategoryJpaEntity, ProductCategoryId> {
    boolean existsByCategoryName(ProductCategoryName categoryName);
}

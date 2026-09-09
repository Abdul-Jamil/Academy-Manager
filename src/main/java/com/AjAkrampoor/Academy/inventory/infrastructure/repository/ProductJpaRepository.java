package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductName;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.ProductJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, ProductId> {
    boolean existsByProductNameAndBranchId(ProductName productName, BranchId branchId);

    Page<ProductJpaEntity> findAllByBranchId(BranchId branchId, Pageable pageable);

    boolean existsByProductNameAndBranchIdAndProductIdNot(ProductName productName, BranchId branchId, ProductId productId);
}

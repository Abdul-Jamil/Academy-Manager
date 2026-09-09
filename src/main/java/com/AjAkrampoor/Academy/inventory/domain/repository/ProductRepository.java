package com.AjAkrampoor.Academy.inventory.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.Product;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository {
    boolean existsById(ProductId id);

    boolean existsByProductNameAndBranchId(ProductName productName, BranchId branchId);

    Optional<Product> findById(ProductId id);

    Product save(Product product);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByBranchId(BranchId branchId, Pageable pageable);

    boolean existsByProductNameAndBranchIdAndProductIdNot(ProductName productName, BranchId branchId, ProductId productId);
}

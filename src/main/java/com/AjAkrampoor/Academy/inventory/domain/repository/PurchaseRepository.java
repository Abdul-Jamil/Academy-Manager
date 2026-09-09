package com.AjAkrampoor.Academy.inventory.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.Purchase;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRepository {
    boolean existsById(PurchaseId id);

    Optional<Purchase> findById(PurchaseId id);

    Purchase save(Purchase purchase);

    Page<Purchase> findAll(Pageable pageable);

    Page<Purchase> findAllByBranchId(BranchId branchId, Pageable pageable);
}

package com.AjAkrampoor.Academy.inventory.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.Sale;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository {
    boolean existsById(SaleId id);

    Optional<Sale> findById(SaleId id);

    Sale save(Sale sale);

    Page<Sale> findAll(Pageable pageable);

    Page<Sale> findAllByBranchId(BranchId branchId, Pageable pageable);
}

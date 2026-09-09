package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.PurchaseJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseJpaRepository extends JpaRepository<PurchaseJpaEntity, PurchaseId> {
    Page<PurchaseJpaEntity> findAllByBranchId(BranchId branchId, Pageable pageable);
}

package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseItemId;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.PurchaseItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseItemJpaRepository extends JpaRepository<PurchaseItemJpaEntity, PurchaseItemId> {
    List<PurchaseItemJpaEntity> findByPurchaseId(PurchaseId purchaseId);
}

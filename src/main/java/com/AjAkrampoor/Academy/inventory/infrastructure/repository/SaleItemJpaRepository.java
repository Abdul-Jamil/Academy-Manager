package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.SaleId;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleItemId;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.SaleItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemJpaRepository extends JpaRepository<SaleItemJpaEntity, SaleItemId> {
    List<SaleItemJpaEntity> findBySaleId(SaleId saleId);

    void deleteBySaleId(SaleId saleId);
}

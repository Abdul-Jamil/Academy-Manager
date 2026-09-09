package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.SupplierId;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierName;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.SupplierJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierJpaRepository extends JpaRepository<SupplierJpaEntity, SupplierId> {
    boolean existsBySupplierName(SupplierName supplierName);

    boolean existsBySupplierNameAndSupplierIdNot(SupplierName supplierName, SupplierId supplierId);
}

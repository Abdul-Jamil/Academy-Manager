package com.AjAkrampoor.Academy.inventory.domain.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierId;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository {
    boolean existsById(SupplierId id);

    boolean existsByName(SupplierName name);

    boolean existsByNameAndIdNot(SupplierName name, SupplierId id);

    Optional<Supplier> findById(SupplierId id);

    Supplier save(Supplier supplier);

    Page<Supplier> findAll(Pageable pageable);
}

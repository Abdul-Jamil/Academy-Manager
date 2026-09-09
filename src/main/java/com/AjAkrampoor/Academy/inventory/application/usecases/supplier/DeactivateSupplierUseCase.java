package com.AjAkrampoor.Academy.inventory.application.usecases.supplier;

import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierId;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierStatus;
import com.AjAkrampoor.Academy.inventory.domain.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeactivateSupplierUseCase {
    private final SupplierRepository supplierRepository;

    public DeactivateSupplierUseCase(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public Supplier execute(UUID supplierUUID) {
        Supplier supplier = supplierRepository.findById(new SupplierId(supplierUUID))
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        if (!supplier.getSupplierStatus().equals(SupplierStatus.ACTIVE)) {
            return supplier;
        }

        supplier.deactivate();
        return supplierRepository.save(supplier);
    }
}

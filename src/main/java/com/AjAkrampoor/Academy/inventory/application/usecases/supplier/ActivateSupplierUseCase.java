package com.AjAkrampoor.Academy.inventory.application.usecases.supplier;

import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierId;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierStatus;
import com.AjAkrampoor.Academy.inventory.domain.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ActivateSupplierUseCase {
    private final SupplierRepository supplierRepository;

    public ActivateSupplierUseCase(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public Supplier execute(UUID supplierUUID) {
        Supplier supplier = supplierRepository.findById(new SupplierId(supplierUUID))
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        if (supplier.getSupplierStatus().equals(SupplierStatus.ACTIVE)) {
            return supplier;
        }

        supplier.activate();
        return supplierRepository.save(supplier);
    }
}

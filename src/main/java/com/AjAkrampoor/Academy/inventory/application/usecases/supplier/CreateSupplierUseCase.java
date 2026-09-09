package com.AjAkrampoor.Academy.inventory.application.usecases.supplier;

import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierId;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierName;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierStatus;
import com.AjAkrampoor.Academy.inventory.domain.repository.SupplierRepository;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateSupplierUseCase {
    private final SupplierRepository supplierRepository;

    public CreateSupplierUseCase(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public Supplier execute(String nameStr, String phoneNumberStr, String address, String description) {
        SupplierName name = new SupplierName(nameStr);
        PhoneNumber phone = new PhoneNumber(phoneNumberStr);

        if (supplierRepository.existsByName(name)) {
            throw new IllegalArgumentException("Supplier with this name already exists");
        }

        for (int i = 0; i < 10; i++) {
            SupplierId id = SupplierId.newId();
            if (!supplierRepository.existsById(id)) {
                Supplier supplier = new Supplier(id, name, phone, address, description, SupplierStatus.ACTIVE);
                return supplierRepository.save(supplier);
            }
        }

        throw new IllegalArgumentException("Could not create supplier after several retries");
    }
}

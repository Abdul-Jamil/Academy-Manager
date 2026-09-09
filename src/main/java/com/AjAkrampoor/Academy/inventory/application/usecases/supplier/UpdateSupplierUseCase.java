package com.AjAkrampoor.Academy.inventory.application.usecases.supplier;

import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierId;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierName;
import com.AjAkrampoor.Academy.inventory.domain.repository.SupplierRepository;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateSupplierUseCase {
    private final SupplierRepository supplierRepository;

    public UpdateSupplierUseCase(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public Supplier execute(UUID supplierUUID,
                            JsonNullable<String> name,
                            JsonNullable<String> phoneNumber,
                            JsonNullable<String> address,
                            JsonNullable<String> description) {
        Supplier supplier = supplierRepository.findById(new SupplierId(supplierUUID))
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        // Update name if present
        if (name.isPresent()) {
            String newName = name.get();
            if (newName != null && !newName.trim().isEmpty()) {
                SupplierName newSupplierName = new SupplierName(newName);
                if (supplierRepository.existsByNameAndIdNot(newSupplierName, supplier.getSupplierId())) {
                    throw new IllegalArgumentException("Supplier with this name already exists");
                }
                supplier.updateName(newSupplierName);
            }
        }

        // Update phone if present
        if (phoneNumber.isPresent()) {
            String phone = phoneNumber.get();
            if (phone != null && !phone.trim().isEmpty()) {
                supplier.updatePhone(new PhoneNumber(phone));
            }
        }

        // Update address if present
        if (address.isPresent()) {
            supplier.updateAddress(address.get());
        }

        // Update description if present
        if (description.isPresent()) {
            supplier.updateDescription(description.get());
        }

        return supplierRepository.save(supplier);
    }
}

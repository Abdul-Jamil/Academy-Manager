package com.AjAkrampoor.Academy.inventory.application;

import com.AjAkrampoor.Academy.inventory.application.dto.SupplierResponse;
import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierResponseAssembler {
    public SupplierResponse toResponse(Supplier supplier) {
        return new SupplierResponse(
                supplier.getSupplierId().toString(),
                supplier.getSupplierName().getValue(),
                supplier.getPhone().getNumber(),
                supplier.getAddress(),
                supplier.getDescription(),
                supplier.getSupplierStatus()
        );
    }
}

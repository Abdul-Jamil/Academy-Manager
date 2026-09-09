package com.AjAkrampoor.Academy.inventory.application.usecases.supplier;

import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.inventory.domain.repository.SupplierRepository;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetSuppliersUseCase {
    private final SupplierRepository supplierRepository;

    public GetSuppliersUseCase(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<Supplier> execute(PaginationRequest paginationRequest) {
        Pageable pageable = paginationRequest.toPageable();
        Page<Supplier> supplierPage = supplierRepository.findAll(pageable);
        return new PaginatedResponse<>(supplierPage);
    }
}

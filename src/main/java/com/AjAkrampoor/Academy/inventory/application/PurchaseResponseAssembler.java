package com.AjAkrampoor.Academy.inventory.application;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.inventory.application.dto.PurchaseResponse;
import com.AjAkrampoor.Academy.inventory.domain.model.Purchase;
import com.AjAkrampoor.Academy.inventory.domain.model.Supplier;
import com.AjAkrampoor.Academy.inventory.domain.repository.SupplierRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class PurchaseResponseAssembler {
    private final SupplierRepository supplierRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public PurchaseResponseAssembler(SupplierRepository supplierRepository, BranchRepository branchRepository, UserRepository userRepository, StaffRepository staffRepository) {
        this.supplierRepository = supplierRepository;
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    public PurchaseResponse toResponse(Purchase purchase) {
        String supplierName = null;
        if (purchase.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(purchase.getSupplierId()).orElseThrow(() -> new RuntimeException("Supplier not found"));
            supplierName = supplier.getSupplierName().getValue();
        }

        Branch branch = branchRepository.findById(purchase.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found"));

        String createdBy = null;
        if (purchase.getCreatedBy() != null) {
            User user = userRepository.findById(purchase.getCreatedBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            createdBy = staff.getName().getFullName();
        }
        String description = (purchase.getDescription() != null) ? purchase.getDescription().getValue() : null;
        return new PurchaseResponse(
                purchase.getPurchaseId().toString(),
                supplierName,
                branch.getName().toString(),
                purchase.getTotalAmount().getAmount(),
                description,
                purchase.getPurchasedAt(),
                createdBy,
                purchase.getPurchaseStatus()
        );
    }
}

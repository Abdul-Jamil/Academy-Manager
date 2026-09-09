package com.AjAkrampoor.Academy.inventory.application;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.inventory.application.dto.SaleResponse;
import com.AjAkrampoor.Academy.inventory.domain.model.Sale;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SaleResponseAssembler {
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public SaleResponseAssembler(BranchRepository branchRepository, UserRepository userRepository, StaffRepository staffRepository) {
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    public SaleResponse toResponse(Sale sale) {
        String description = (sale.getDescription() != null) ? sale.getDescription().getValue() : null;
        BigDecimal discount = (sale.getDiscountAmount() != null) ? sale.getDiscountAmount().getAmount() : null;

        String soldBy = null;
        if (sale.getSoldBy() != null) {
            User user = userRepository.findById(sale.getSoldBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            soldBy = staff.getName().getFullName();
        }

        Branch branch = branchRepository.findById(sale.getBranchId()).orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        return new SaleResponse(
                sale.getSaleId().toString(),
                branch.getName().toString(),
                sale.getTotalAmount().getAmount(),
                discount,
                soldBy,
                sale.getSoldAt(),
                description,
                sale.getSaleStatus()
        );
    }
}

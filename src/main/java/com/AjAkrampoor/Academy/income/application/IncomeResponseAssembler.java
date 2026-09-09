package com.AjAkrampoor.Academy.income.application;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.income.application.dto.IncomeResponse;
import com.AjAkrampoor.Academy.income.domain.model.Profit;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitCategoryRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class IncomeResponseAssembler {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final BranchRepository branchRepository;
    private final ProfitCategoryRepository profitCategoryRepository;

    public IncomeResponseAssembler(UserRepository userRepository, StaffRepository staffRepository, BranchRepository branchRepository, ProfitCategoryRepository profitCategoryRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.branchRepository = branchRepository;
        this.profitCategoryRepository = profitCategoryRepository;
    }

    public IncomeResponse toResponse(Profit profit) {

        String createdBy = null;
        if (profit.getCreatedBy() != null) {
            User user = userRepository.findById(profit.getCreatedBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            createdBy = staff.getName().getFullName();
        }

        String deletedBy = null;
        if (profit.getDeletedBy() != null) {
            User user = userRepository.findById(profit.getDeletedBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            deletedBy = staff.getName().getFullName();
        }

        Branch branch = branchRepository.findById(profit.getBranchId()).orElseThrow(() -> new IllegalArgumentException("Branch not found"));
        ProfitCategory profitCategory = profitCategoryRepository.findById(profit.getProfitCategoryId()).orElseThrow(() -> new IllegalArgumentException("ProfitCategory not found"));

        return new IncomeResponse(
                profit.getProfitId().toString(),
                profit.getDescription().getValue(),
                profit.getAmount().getAmount(),
                createdBy,
                profit.getCreatedAt(),
                profitCategory.getCategoryName().getValue(),
                profit.getProfitStatus(),
                deletedBy,
                profit.getDeletedAt(),
                branch.getName().toString()
        );
    }
}

package com.AjAkrampoor.Academy.expenses.application;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.expenses.application.dto.ExpenseResponse;
import com.AjAkrampoor.Academy.expenses.domain.model.Expense;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
import com.AjAkrampoor.Academy.expenses.domain.repository.ExpenseCategoryRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ExpenseResponseAssembler {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final BranchRepository branchRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseResponseAssembler(UserRepository userRepository, StaffRepository staffRepository, BranchRepository branchRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.branchRepository = branchRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public ExpenseResponse toResponse(Expense expense) {

        String createdBy = null;
        if (expense.getCreatedBy() != null) {
            User user = userRepository.findById(expense.getCreatedBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            createdBy = staff.getName().getFullName();
        }

        String deletedBy = null;
        if (expense.getDeletedBy() != null) {
            User user = userRepository.findById(expense.getDeletedBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            deletedBy = staff.getName().getFullName();
        }

        Branch branch = branchRepository.findById(expense.getBranchId()).orElseThrow(() -> new IllegalArgumentException("No such branch found"));

        ExpenseCategory category = expenseCategoryRepository.findById(expense.getExpenseCategoryId()).orElseThrow(() -> new IllegalArgumentException("ExpenseCategory not found"));

        return new ExpenseResponse
                (
                        expense.getExpenseId().toString(),
                        expense.getDescription().getValue(),
                        expense.getAmount().getAmount(),
                        createdBy,
                        expense.getCreatedAt(),
                        category.getCategoryName().getValue(),
                        expense.getExpenseStatus(),
                        deletedBy,
                        expense.getDeletedAt(),
                        branch.getName().toString()
                );
    }
}

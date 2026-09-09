package com.AjAkrampoor.Academy.expenses.application.usecases;

import com.AjAkrampoor.Academy.expenses.domain.model.Expense;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseId;
import com.AjAkrampoor.Academy.expenses.domain.repository.ExpenseRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteExpenseUseCase {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public DeleteExpenseUseCase(ExpenseRepository expenseRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Expense execute(String userId, UUID expenseUUID) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Expense expense = expenseRepository.findById(new ExpenseId(expenseUUID)).orElseThrow(() -> new IllegalArgumentException("No such expense found"));

        if (!userRole.isSuperAdmin()) {
            if (!staff.getBranchId().equals(expense.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        expense.delete(user.getUserId());

        return expenseRepository.save(expense);
    }
}

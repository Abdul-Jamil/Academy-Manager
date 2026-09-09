package com.AjAkrampoor.Academy.expenses.application.usecases;

import com.AjAkrampoor.Academy.expenses.domain.model.Expense;
import com.AjAkrampoor.Academy.expenses.domain.repository.ExpenseRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetExpensesUseCase {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public GetExpensesUseCase(ExpenseRepository expenseRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<Expense> execute(String userId, PaginationRequest paginationRequest) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        Pageable pageable = paginationRequest.toPageable();
        Page<Expense> expensePage;
        if (userRole.isSuperAdmin()) {
            expensePage = expenseRepository.findAll(pageable);
        } else {
            expensePage = expenseRepository.findAllByBranchId(staff.getBranchId(), pageable);
        }

        return new PaginatedResponse<>(expensePage);
    }
}

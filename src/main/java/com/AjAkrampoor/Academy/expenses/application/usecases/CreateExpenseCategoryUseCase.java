package com.AjAkrampoor.Academy.expenses.application.usecases;

import com.AjAkrampoor.Academy.expenses.domain.model.CategoryStatus;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategory;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryName;
import com.AjAkrampoor.Academy.expenses.domain.repository.ExpenseCategoryRepository;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateExpenseCategoryUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;


    public CreateExpenseCategoryUseCase(UserRepository userRepository, RoleRepository roleRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Transactional
    public ExpenseCategory execute(String nameStr, String descriptionStr) {

        ExpenseCategoryName name = new ExpenseCategoryName(nameStr);
        Description description = (descriptionStr != null && !descriptionStr.isBlank()) ? new Description(descriptionStr) : null;

        if (expenseCategoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("This category name already exists");
        }

        ExpenseCategoryId id;
        for (int i = 0; i < 10; i++) {
            id = ExpenseCategoryId.newId();
            if (!expenseCategoryRepository.existsById(id)) {

                return expenseCategoryRepository.save(new ExpenseCategory(id, name, description, CategoryStatus.ACTIVE));
            }
        }

        throw new IllegalArgumentException("Could not create expense category after several retries.");
    }
}

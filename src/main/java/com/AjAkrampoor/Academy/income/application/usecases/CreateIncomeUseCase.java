package com.AjAkrampoor.Academy.income.application.usecases;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.income.domain.model.*;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitCategoryRepository;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateIncomeUseCase {
    private final ProfitRepository profitRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final StaffRepository staffRepository;
    private final ProfitCategoryRepository categoryRepository;

    public CreateIncomeUseCase(ProfitRepository profitRepository, UserRepository userRepository,
                               RoleRepository roleRepository, BranchRepository branchRepository,
                               StaffRepository staffRepository, ProfitCategoryRepository categoryRepository) {
        this.profitRepository = profitRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.branchRepository = branchRepository;
        this.staffRepository = staffRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Profit execute(String userId, BigDecimal amount, UUID categoryId, UUID branchUUID, String descriptionStr) {
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        ProfitCategory category = categoryRepository.findById(new ProfitCategoryId(categoryId))
                .orElseThrow(() -> new IllegalArgumentException("No such category found"));

        BranchId finalBranchId = staff.getBranchId();

        if (branchUUID != null && userRole.isSuperAdmin()) {
            Branch branch = branchRepository.findById(new BranchId(branchUUID))
                    .orElseThrow(() -> new IllegalArgumentException("No such branch found"));
            if (!branch.isActive()) {
                throw new IllegalArgumentException("Branch is not active");
            }
            finalBranchId = branch.getId();
        }

        if (descriptionStr == null || descriptionStr.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        Money profitMoney = new Money(amount);
        Description description = new Description(descriptionStr);

        ProfitId profitId;
        for (int i = 0; i < 10; i++) {
            profitId = ProfitId.newId();
            if (!profitRepository.existsById(profitId)) {
                return profitRepository.save(new Profit(
                        profitId,
                        description,
                        profitMoney,
                        user.getUserId(),
                        LocalDateTime.now(),
                        category.getCategoryId(),
                        ProfitStatus.ACTIVE,
                        null,
                        null,
                        finalBranchId
                ));
            }
        }

        throw new IllegalArgumentException("Could not create profit after several retries");
    }
}

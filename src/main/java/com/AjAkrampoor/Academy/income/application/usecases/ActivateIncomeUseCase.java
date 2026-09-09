package com.AjAkrampoor.Academy.income.application.usecases;

import com.AjAkrampoor.Academy.income.domain.model.Profit;
import com.AjAkrampoor.Academy.income.domain.model.ProfitId;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitRepository;
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
public class ActivateIncomeUseCase {
    private final ProfitRepository profitRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public ActivateIncomeUseCase(ProfitRepository profitRepository, UserRepository userRepository,
                                 RoleRepository roleRepository, StaffRepository staffRepository) {
        this.profitRepository = profitRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Profit execute(String userId, UUID profitUUID) {
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        Profit profit = profitRepository.findById(new ProfitId(profitUUID))
                .orElseThrow(() -> new IllegalArgumentException("No such profit found"));

        if (!userRole.isSuperAdmin()) {
            if (!staff.getBranchId().equals(profit.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        profit.activate(); // added method in Profit
        return profitRepository.save(profit);
    }
}

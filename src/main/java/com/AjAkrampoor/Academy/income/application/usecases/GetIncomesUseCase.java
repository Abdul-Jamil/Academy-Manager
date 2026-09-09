package com.AjAkrampoor.Academy.income.application.usecases;

import com.AjAkrampoor.Academy.income.domain.model.Profit;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitRepository;
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
public class GetIncomesUseCase {
    private final ProfitRepository profitRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public GetIncomesUseCase(ProfitRepository profitRepository, UserRepository userRepository,
                             RoleRepository roleRepository, StaffRepository staffRepository) {
        this.profitRepository = profitRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<Profit> execute(String userId, PaginationRequest paginationRequest) {
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        Pageable pageable = paginationRequest.toPageable();
        Page<Profit> profitPage;
        if (userRole.isSuperAdmin()) {
            profitPage = profitRepository.findAll(pageable);
        } else {
            profitPage = profitRepository.findAllByBranchId(staff.getBranchId(), pageable);
        }

        return new PaginatedResponse<>(profitPage);
    }
}

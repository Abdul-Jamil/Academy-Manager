package com.AjAkrampoor.Academy.inventory.application.usecases.purchase;

import com.AjAkrampoor.Academy.inventory.domain.model.Purchase;
import com.AjAkrampoor.Academy.inventory.domain.repository.PurchaseRepository;
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
public class GetPurchasesUseCase {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public GetPurchasesUseCase(PurchaseRepository purchaseRepository,
                               UserRepository userRepository,
                               RoleRepository roleRepository,
                               StaffRepository staffRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<Purchase> execute(String userId, PaginationRequest paginationRequest) {
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        Pageable pageable = paginationRequest.toPageable();
        Page<Purchase> purchasePage;
        if (role.isSuperAdmin()) {
            purchasePage = purchaseRepository.findAll(pageable);
        } else {
            purchasePage = purchaseRepository.findAllByBranchId(staff.getBranchId(), pageable);
        }
        return new PaginatedResponse<>(purchasePage);
    }
}

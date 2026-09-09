package com.AjAkrampoor.Academy.inventory.application.usecases.purchase;

import com.AjAkrampoor.Academy.inventory.domain.model.Purchase;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import com.AjAkrampoor.Academy.inventory.domain.repository.PurchaseRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdatePurchaseDescriptionUseCase {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public UpdatePurchaseDescriptionUseCase(PurchaseRepository purchaseRepository,
                                            UserRepository userRepository,
                                            RoleRepository roleRepository,
                                            StaffRepository staffRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Purchase execute(String userId, UUID purchaseUUID, JsonNullable<String> newDescription) {
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        Purchase purchase = purchaseRepository.findById(new PurchaseId(purchaseUUID))
                .orElseThrow(() -> new IllegalArgumentException("Purchase not found"));

        // Branch access
        if (!role.isSuperAdmin() && !staff.getBranchId().equals(purchase.getBranchId())) {
            throw new IllegalArgumentException("Cannot access other branches' data");
        }

        // Update description if present
        if (newDescription.isPresent()) {
            String desc = newDescription.get();
            Description description = (desc == null || desc.isBlank()) ? null : new Description(desc);
            purchase.updateDescription(description);
        }

        return purchaseRepository.save(purchase);
    }
}

package com.AjAkrampoor.Academy.inventory.application.usecases.sale;

import com.AjAkrampoor.Academy.inventory.domain.model.Sale;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleId;
import com.AjAkrampoor.Academy.inventory.domain.repository.SaleRepository;
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
public class UpdateSaleDescriptionUseCase {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public UpdateSaleDescriptionUseCase(SaleRepository saleRepository,
                                        UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        StaffRepository staffRepository) {
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Sale execute(String userId, UUID saleUUID, JsonNullable<String> newDescription) {
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        Sale sale = saleRepository.findById(new SaleId(saleUUID))
                .orElseThrow(() -> new IllegalArgumentException("Sale not found"));

        // Branch access
        if (!role.isSuperAdmin() && !staff.getBranchId().equals(sale.getBranchId())) {
            throw new IllegalArgumentException("Cannot access other branches' data");
        }

        // Update description if present
        if (newDescription.isPresent()) {
            String desc = newDescription.get();
            Description description = (desc == null || desc.isBlank()) ? null : new Description(desc);
            sale.updateDescription(description);
        }

        return saleRepository.save(sale);
    }
}

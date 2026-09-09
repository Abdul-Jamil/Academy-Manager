package com.AjAkrampoor.Academy.inventory.application.usecases.product;

import com.AjAkrampoor.Academy.inventory.domain.model.Product;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductId;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductRepository;
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
public class DeactivateProductUseCase {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public DeactivateProductUseCase(ProductRepository productRepository,
                                    UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    StaffRepository staffRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Product execute(String userId, UUID productUUID) {
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        Product product = productRepository.findById(new ProductId(productUUID))
                .orElseThrow(() -> new IllegalArgumentException("No such product found"));

        if (!userRole.isSuperAdmin()) {
            if (!staff.getBranchId().equals(product.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        if (!product.isActive()) {
            return product;
        }

        product.deactivate();
        return productRepository.save(product);
    }
}

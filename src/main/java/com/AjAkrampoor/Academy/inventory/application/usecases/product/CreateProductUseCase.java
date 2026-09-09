package com.AjAkrampoor.Academy.inventory.application.usecases.product;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.inventory.domain.model.*;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductCategoryRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductRepository;
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
public class CreateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final BranchRepository branchRepository;

    public CreateProductUseCase(ProductRepository productRepository,
                                ProductCategoryRepository categoryRepository,
                                UserRepository userRepository,
                                RoleRepository roleRepository,
                                StaffRepository staffRepository,
                                BranchRepository branchRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.branchRepository = branchRepository;
    }

    @Transactional
    public Product execute(String userId, String productNameStr, String descriptionStr, BigDecimal sellingPrice, Long quantity, UUID categoryUUID, UUID branchUUID) {
        // Validate user, role, staff
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        // Determine branch
        BranchId finalBranchId = staff.getBranchId();
        if (branchUUID != null && userRole.isSuperAdmin()) {
            Branch branch = branchRepository.findById(new BranchId(branchUUID))
                    .orElseThrow(() -> new IllegalArgumentException("No such branch found"));
            if (!branch.isActive()) {
                throw new IllegalArgumentException("Branch is not active");
            }
            finalBranchId = branch.getId();
        }

        // Validate product category
        ProductCategory category = categoryRepository.findById(new ProductCategoryId(categoryUUID))
                .orElseThrow(() -> new IllegalArgumentException("No such product category found"));
        if (!category.isActive()) {
            throw new IllegalArgumentException("Product category is not active");
        }

        // Check unique product name per branch
        ProductName productName = new ProductName(productNameStr);
        if (productRepository.existsByProductNameAndBranchId(productName, finalBranchId)) {
            throw new IllegalArgumentException("Product with this name already exists in the branch");
        }

        Description description = (descriptionStr != null && !descriptionStr.isBlank()) ?
                new Description(descriptionStr) : null;

        Money sellingPriceMoney = new Money(sellingPrice);

        // Generate unique ID
        for (int i = 0; i < 10; i++) {
            ProductId productId = ProductId.newId();
            if (!productRepository.existsById(productId)) {
                Product product = new Product(
                        productId,
                        productName,
                        description,
                        category.getCategoryId(),
                        sellingPriceMoney,
                        quantity,
                        ProductStatus.ACTIVE,
                        user.getUserId(),
                        LocalDateTime.now(),
                        finalBranchId
                );
                return productRepository.save(product);
            }
        }
        throw new IllegalArgumentException("Could not create product after several retries");
    }
}

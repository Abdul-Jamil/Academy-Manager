package com.AjAkrampoor.Academy.inventory.application.usecases.product;

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
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UpdateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public UpdateProductUseCase(ProductRepository productRepository,
                                ProductCategoryRepository categoryRepository,
                                UserRepository userRepository,
                                RoleRepository roleRepository,
                                StaffRepository staffRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Product execute(String userId, UUID productUUID,
                           JsonNullable<String> productName,
                           JsonNullable<String> description,
                           JsonNullable<BigDecimal> sellingPrice,
                           UUID categoryUUID) {
        // Validate user and permissions
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        Product product = productRepository.findById(new ProductId(productUUID))
                .orElseThrow(() -> new IllegalArgumentException("No such product found"));

        // Check branch access
        if (!userRole.isSuperAdmin()) {
            if (!staff.getBranchId().equals(product.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        if (!product.isActive()) {
            throw new IllegalStateException("Cannot update an inactive product.");
        }

        // Update category if provided
        if (categoryUUID != null) {
            ProductCategory category = categoryRepository.findById(new ProductCategoryId(categoryUUID))
                    .orElseThrow(() -> new IllegalArgumentException("No such product category found"));
            if (!category.isActive()) {
                throw new IllegalArgumentException("Product category is not active");
            }
            product.updateCategory(category.getCategoryId());
        }

        // Update name if present
        if (productName.isPresent()) {
            String newName = productName.get();
            if (newName != null && !newName.trim().isEmpty()) {
                ProductName newProductName = new ProductName(newName);
                // Check uniqueness excluding current product
                if (productRepository.existsByProductNameAndBranchIdAndProductIdNot(
                        newProductName, product.getBranchId(), product.getProductId())) {
                    throw new IllegalArgumentException("Product with this name already exists in the branch");
                }
                product.updateName(newProductName);
            }
        }

        // Update description if present
        if (description.isPresent()) {
            String descValue = description.get();
            Description newDescription = (descValue == null) ? null : new Description(descValue);
            product.updateDescription(newDescription);
        }

        // Update selling price if present
        if (sellingPrice.isPresent()) {
            BigDecimal price = sellingPrice.get();
            if (price != null) {
                Money newPrice = new Money(price);
                product.updateSellingPrice(newPrice);
            }
        }

        return productRepository.save(product);
    }
}

package com.AjAkrampoor.Academy.inventory.application;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.inventory.application.dto.ProductResponse;
import com.AjAkrampoor.Academy.inventory.domain.model.Product;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductCategoryRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseAssembler {
    private final ProductCategoryRepository productCategoryRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final BranchRepository branchRepository;

    public ProductResponseAssembler(ProductCategoryRepository productCategoryRepository, UserRepository userRepository, StaffRepository staffRepository, BranchRepository branchRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.branchRepository = branchRepository;
    }

    public ProductResponse toResponse(Product product) {
        String description = (product.getDescription() != null) ? product.getDescription().getValue() : null;

        String createdBy = null;
        if (product.getCreatedBy() != null) {
            User user = userRepository.findById(product.getCreatedBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            createdBy = staff.getName().getFullName();
        }

        ProductCategory category = productCategoryRepository.findById(product.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        Branch branch = branchRepository.findById(product.getBranchId()).orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        return new ProductResponse(
                product.getProductId().toString(),
                product.getProductName().getValue(),
                description,
                category.getCategoryName().getValue(),
                product.getSellingPrice().getAmount(),
                product.getQuantity(),
                product.getProductStatus(),
                createdBy,
                product.getCreatedAt(),
                branch.getName().toString()
        );
    }
}
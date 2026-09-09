package com.AjAkrampoor.Academy.inventory.application.usecases.sale;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.inventory.domain.model.*;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.SaleItemRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.SaleRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CreateSaleUseCase {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final BranchRepository branchRepository;

    public CreateSaleUseCase(SaleRepository saleRepository,
                             SaleItemRepository saleItemRepository,
                             ProductRepository productRepository,
                             UserRepository userRepository,
                             RoleRepository roleRepository,
                             StaffRepository staffRepository,
                             BranchRepository branchRepository) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.branchRepository = branchRepository;
    }

    @Transactional
    public Sale execute(String userId, List<SaleItemRequest> items, BigDecimal discountAmount, String descriptionStr, UUID branchUUID) {
        // Validate user, role, staff
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        // Determine branch
        BranchId finalBranchId = staff.getBranchId();
        if (branchUUID != null && role.isSuperAdmin()) {
            Branch branch = branchRepository.findById(new BranchId(branchUUID))
                    .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
            if (!branch.isActive()) {
                throw new IllegalArgumentException("Branch is not active");
            }
            finalBranchId = branch.getId();
        }

        // Validate items
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Sale must have at least one item");
        }

        Money totalAmount = new Money(BigDecimal.ZERO);
        List<SaleItem> saleItems = new ArrayList<>();
        SaleId saleId = generateUniqueSaleId();

        // Process each item
        for (SaleItemRequest itemReq : items) {
            Product product = productRepository.findById(new ProductId(itemReq.productId()))
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemReq.productId()));

            if (!product.isActive()) {
                throw new IllegalArgumentException("Product is not active: " + itemReq.productId());
            }
            if (!product.getBranchId().equals(finalBranchId)) {
                throw new IllegalArgumentException("Product does not belong to the selected branch: " + itemReq.productId());
            }

            long requested = itemReq.quantity();
            if (requested <= 0) {
                throw new IllegalArgumentException("Quantity must be positive for product: " + itemReq.productId());
            }
            if (product.getQuantity() == null || product.getQuantity() < requested) {
                throw new IllegalArgumentException("Insufficient quantity for product: " + itemReq.productId() +
                        ". Available: " + (product.getQuantity() == null ? 0 : product.getQuantity()));
            }

            // Decrease product quantity
            product.decreaseQuantity(requested);
            productRepository.save(product);

            // Calculate item total
            Money unitPrice = product.getSellingPrice();
            Money itemTotal = unitPrice.multiply(requested);
            totalAmount = totalAmount.add(itemTotal);

            SaleItem saleItem = new SaleItem(
                    SaleItemId.newId(),
                    saleId,
                    product.getProductId(),
                    (int) requested,
                    unitPrice,
                    itemTotal
            );
            saleItems.add(saleItem);
        }

        // Apply discount
        Money discount = (discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) > 0)
                ? new Money(discountAmount)
                : null;
        if (discount != null) {
            if (discount.getAmount().compareTo(totalAmount.getAmount()) > 0) {
                throw new IllegalArgumentException("Discount cannot exceed total amount");
            }
            totalAmount = totalAmount.subtract(discount);
        }

        // Create Sale
        Description description = (descriptionStr != null && !descriptionStr.isBlank())
                ? new Description(descriptionStr)
                : null;

        Sale sale = new Sale(
                saleId,
                finalBranchId,
                totalAmount,
                discount,
                user.getUserId(),
                LocalDateTime.now(),
                description,
                SaleStatus.COMPLETED
        );

        Sale savedSale = saleRepository.save(sale);

        // Save sale items
        saleItemRepository.saveAll(saleItems);

        return savedSale;
    }

    private SaleId generateUniqueSaleId() {
        for (int i = 0; i < 10; i++) {
            SaleId id = SaleId.newId();
            if (!saleRepository.existsById(id)) {
                return id;
            }
        }
        throw new IllegalArgumentException("Could not generate unique sale ID after retries");
    }

    public record SaleItemRequest(UUID productId, long quantity) {
    }
}

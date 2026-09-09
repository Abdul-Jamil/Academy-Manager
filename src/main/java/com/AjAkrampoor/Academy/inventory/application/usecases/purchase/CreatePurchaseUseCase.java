package com.AjAkrampoor.Academy.inventory.application.usecases.purchase;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.inventory.domain.model.*;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.PurchaseItemRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.PurchaseRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.SupplierRepository;
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
public class CreatePurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final BranchRepository branchRepository;

    public CreatePurchaseUseCase(PurchaseRepository purchaseRepository,
                                 PurchaseItemRepository purchaseItemRepository,
                                 ProductRepository productRepository,
                                 SupplierRepository supplierRepository,
                                 UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 StaffRepository staffRepository,
                                 BranchRepository branchRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.branchRepository = branchRepository;
    }

    @Transactional
    public Purchase execute(String userId, UUID supplierUUID, List<PurchaseItemRequest> items,
                            String descriptionStr, UUID branchUUID) {
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

        // Validate supplier (if provided)
        SupplierId supplierId = null;
        if (supplierUUID != null) {
            Supplier supplier = supplierRepository.findById(new SupplierId(supplierUUID))
                    .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
            if (!supplier.getSupplierStatus().equals(SupplierStatus.ACTIVE)) {
                throw new IllegalArgumentException("Supplier is not active");
            }
            supplierId = supplier.getSupplierId();
        }

        // Validate items
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Purchase must have at least one item");
        }

        Money totalAmount = new Money(BigDecimal.ZERO);
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        PurchaseId purchaseId = generateUniquePurchaseId();

        // Process each item
        for (PurchaseItemRequest itemReq : items) {
            Product product = productRepository.findById(new ProductId(itemReq.productId()))
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemReq.productId()));

            if (!product.isActive()) {
                throw new IllegalArgumentException("Product is not active: " + itemReq.productId());
            }
            if (!product.getBranchId().equals(finalBranchId)) {
                throw new IllegalArgumentException("Product does not belong to the selected branch: " + itemReq.productId());
            }

            long quantity = itemReq.quantity();
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive for product: " + itemReq.productId());
            }

            Money unitCost = new Money(itemReq.unitCost());
            Money itemTotal = unitCost.multiply(quantity);
            totalAmount = totalAmount.add(itemTotal);

            // Increase product quantity (purchase adds stock)
            product.increaseQuantity(quantity);
            productRepository.save(product);

            PurchaseItem purchaseItem = new PurchaseItem(
                    PurchaseItemId.newId(),
                    purchaseId,
                    product.getProductId(),
                    (int) quantity,
                    unitCost,
                    itemTotal
            );
            purchaseItems.add(purchaseItem);
        }

        // Create Purchase
        Description description = (descriptionStr != null && !descriptionStr.isBlank())
                ? new Description(descriptionStr)
                : null;

        Purchase purchase = new Purchase(
                purchaseId,
                supplierId,
                finalBranchId,
                totalAmount,
                description,
                LocalDateTime.now(),
                user.getUserId(),
                PurchaseStatus.COMPLETED
        );

        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Save purchase items
        purchaseItemRepository.saveAll(purchaseItems);

        return savedPurchase;
    }

    private PurchaseId generateUniquePurchaseId() {
        for (int i = 0; i < 10; i++) {
            PurchaseId id = PurchaseId.newId();
            if (!purchaseRepository.existsById(id)) {
                return id;
            }
        }
        throw new IllegalArgumentException("Could not generate unique purchase ID after retries");
    }

    public record PurchaseItemRequest(UUID productId, long quantity, BigDecimal unitCost) {
    }
}

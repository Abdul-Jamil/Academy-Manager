package com.AjAkrampoor.Academy.inventory.application.usecases.purchase;

import com.AjAkrampoor.Academy.inventory.domain.model.Product;
import com.AjAkrampoor.Academy.inventory.domain.model.Purchase;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseItem;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.PurchaseItemRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.PurchaseRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CancelPurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public CancelPurchaseUseCase(PurchaseRepository purchaseRepository,
                                 PurchaseItemRepository purchaseItemRepository,
                                 ProductRepository productRepository,
                                 UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 StaffRepository staffRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Purchase execute(String userId, UUID purchaseUUID) {
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

        // Cancel the purchase (sets status to CANCEL)
        purchase.cancel();

        // Revert quantities: for each purchase item, decrease product quantity (undo the addition)
        List<PurchaseItem> items = purchaseItemRepository.findByPurchaseId(purchase.getPurchaseId());
        for (PurchaseItem item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found for item: " + item.getProductId()));
            // Check if we have enough quantity to remove (we should, because we added it earlier)
            if (product.getQuantity() == null || product.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException("Cannot cancel purchase: insufficient quantity of product " + product.getProductId() +
                        " to revert. Available: " + (product.getQuantity() == null ? 0 : product.getQuantity()) +
                        ", requested to remove: " + item.getQuantity());
            }
            product.decreaseQuantity(item.getQuantity());
            productRepository.save(product);
        }

        // Save purchase (status updated)
        return purchaseRepository.save(purchase);
    }
}

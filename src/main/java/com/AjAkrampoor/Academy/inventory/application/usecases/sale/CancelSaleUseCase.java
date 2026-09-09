package com.AjAkrampoor.Academy.inventory.application.usecases.sale;

import com.AjAkrampoor.Academy.inventory.domain.model.Product;
import com.AjAkrampoor.Academy.inventory.domain.model.Sale;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleId;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleItem;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.SaleItemRepository;
import com.AjAkrampoor.Academy.inventory.domain.repository.SaleRepository;
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
public class CancelSaleUseCase {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public CancelSaleUseCase(SaleRepository saleRepository,
                             SaleItemRepository saleItemRepository,
                             ProductRepository productRepository,
                             UserRepository userRepository,
                             RoleRepository roleRepository,
                             StaffRepository staffRepository) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Sale execute(String userId, UUID saleUUID) {
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

        // Cancel the sale (sets status to CANCEL)
        sale.cancel();

        // Revert quantities: for each sale item, increase product quantity
        List<SaleItem> items = saleItemRepository.findBySaleId(sale.getSaleId());
        for (SaleItem item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found for item: " + item.getProductId()));
            product.increaseQuantity(item.getQuantity());
            productRepository.save(product);
        }

        // Save sale (status updated)
        return saleRepository.save(sale);
    }
}

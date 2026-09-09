package com.AjAkrampoor.Academy.bills.application.usecases;

import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.repository.BillRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateBillDescriptionUseCase {
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;

    public UpdateBillDescriptionUseCase(BillRepository billRepository, UserRepository userRepository, StaffRepository staffRepository, RoleRepository roleRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Bill execute(String userId, UUID billUUID, String descriptionStr) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Bill bill = billRepository.findById(new BillId(billUUID)).orElseThrow(() -> new IllegalArgumentException("No such bill found"));

        if (!userRole.isSuperAdmin() && !bill.getBranchId().equals(staff.getBranchId())) {
            throw new IllegalArgumentException("Cannot access other branches' data");
        }

        if (descriptionStr == null || descriptionStr.isEmpty()) {
            bill.updateDescription(null);
        } else {
            Description description = new Description(descriptionStr);
            bill.updateDescription(description);
        }

        return billRepository.save(bill);
    }
}

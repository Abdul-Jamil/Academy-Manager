package com.AjAkrampoor.Academy.bills.application.usecases;

import com.AjAkrampoor.Academy.bills.application.dto.BillFilter;
import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.repository.BillRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetBillsUseCase {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public GetBillsUseCase(BillRepository billRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    public PaginatedResponse<Bill> execute(String userId, PaginationRequest paginationRequest, BillFilter filter) {

        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new RuntimeException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new RuntimeException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new RuntimeException("No such staff found"));


        if (!userRole.isSuperAdmin()) {
            String userBranchId = staff.getBranchId().toString();

            if (filter.getBranchId() != null && !filter.getBranchId().equals(userBranchId)) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }

            filter.setBranchId(userBranchId);
        }

        Pageable pageable = paginationRequest.toPageable();
        Page<Bill> bills = billRepository.findAll(pageable, filter);

        return new PaginatedResponse<>(bills);
    }
}

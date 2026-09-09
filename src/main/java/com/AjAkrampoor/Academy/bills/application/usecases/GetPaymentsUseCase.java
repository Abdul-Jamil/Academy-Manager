package com.AjAkrampoor.Academy.bills.application.usecases;

import com.AjAkrampoor.Academy.bills.application.dto.PaymentFilter;
import com.AjAkrampoor.Academy.bills.domain.model.Payment;
import com.AjAkrampoor.Academy.bills.domain.repository.PaymentRepository;
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
public class GetPaymentsUseCase {
    private final PaymentRepository paymentRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public GetPaymentsUseCase(PaymentRepository paymentRepository, StaffRepository staffRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public PaginatedResponse<Payment> execute(String userId, PaymentFilter filter, PaginationRequest paginationRequest) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (!userRole.isSuperAdmin()) {
            String userBranchId = staff.getBranchId().toString();

            if (filter.getBranchId() != null && !filter.getBranchId().equals(userBranchId)) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }

            filter.setBranchId(userBranchId);
        }
        Pageable pageable = paginationRequest.toPageable();
        Page<Payment> payments = paymentRepository.findAll(pageable, filter);
        return new PaginatedResponse<>(payments);
    }
}

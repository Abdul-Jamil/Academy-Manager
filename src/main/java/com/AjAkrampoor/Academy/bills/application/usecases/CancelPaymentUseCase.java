package com.AjAkrampoor.Academy.bills.application.usecases;

import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.model.Payment;
import com.AjAkrampoor.Academy.bills.domain.model.PaymentId;
import com.AjAkrampoor.Academy.bills.domain.repository.BillRepository;
import com.AjAkrampoor.Academy.bills.domain.repository.PaymentRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CancelPaymentUseCase {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final PaymentRepository paymentRepository;


    public CancelPaymentUseCase(BillRepository billRepository, UserRepository userRepository, StaffRepository staffRepository, RoleRepository roleRepository, PaymentRepository paymentRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment execute(String userId, UUID paymentUUID) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Payment payment = paymentRepository.findById(new PaymentId(paymentUUID)).orElseThrow(() -> new IllegalArgumentException("No such payment found"));
        Bill bill = billRepository.findById(payment.getBillId()).orElseThrow(() -> new IllegalArgumentException("No such bill found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (!userRole.isSuperAdmin()) {
            if (!staff.getBranchId().equals(bill.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        payment.cancel(user.getUserId());

        return paymentRepository.save(payment);
    }
}

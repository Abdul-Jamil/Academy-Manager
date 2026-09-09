package com.AjAkrampoor.Academy.bills.application.usecases;

import com.AjAkrampoor.Academy.bills.domain.model.*;
import com.AjAkrampoor.Academy.bills.domain.repository.BillRepository;
import com.AjAkrampoor.Academy.bills.domain.repository.PaymentRepository;
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
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class CreatePaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;

    public CreatePaymentUseCase(PaymentRepository paymentRepository, UserRepository userRepository, BillRepository billRepository, StaffRepository staffRepository, RoleRepository roleRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.billRepository = billRepository;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Payment execute(String userId, UUID billUUID, BigDecimal amount, String descriptionStr) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new RuntimeException("No such user found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new RuntimeException("No such staff found"));
        Bill bill = billRepository.findById(new BillId(billUUID)).orElseThrow(() -> new RuntimeException("No such bill found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new RuntimeException("No such role found"));

        if (!userRole.isSuperAdmin()) {
            if (!bill.getBranchId().equals(staff.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        if (bill.getStatus() != BillStatus.ACTIVE) {
            throw new IllegalArgumentException("Cannot pay a cancelled bill");
        }

        Money paidAmount = new Money(amount);

        Map<BillId, BigDecimal> paidMap = paymentRepository.getPaidAmountsByBillIds(Set.of(bill.getBillId()));
        BigDecimal alreadyPaid = paidMap.getOrDefault(bill.getBillId(), BigDecimal.ZERO);

        BigDecimal discount = bill.getDiscount() != null ? bill.getDiscount().getAmount() : BigDecimal.ZERO;
        BigDecimal netPayable = bill.getAmount().getAmount().subtract(discount);

        BigDecimal newTotal = alreadyPaid.add(amount);
        if (newTotal.compareTo(netPayable) > 0) {
            throw new IllegalArgumentException(
                    "Payment would exceed the bill's net amount. Already paid: " + alreadyPaid +
                            ", net payable: " + netPayable
            );
        }

        Description description = null;
        if (descriptionStr != null && !descriptionStr.trim().isEmpty()) {
            description = new Description(descriptionStr);
        }

        PaymentId paymentId;
        for (int i = 0; i < 10; i++) {
            paymentId = PaymentId.newId();
            if (!paymentRepository.existsById(paymentId)) {
                return paymentRepository.save(new Payment
                        (
                                paymentId,
                                bill.getBillId(),
                                user.getUserId(),
                                paidAmount,
                                description,
                                LocalDateTime.now(),
                                null,
                                null,
                                PaymentStatus.PAID
                        ));
            }
        }

        throw new IllegalArgumentException("Could not create payment after several retries");
    }
}

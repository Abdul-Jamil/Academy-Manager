package com.AjAkrampoor.Academy.enrollments.application.usecases;

import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.repository.BillRepository;
import com.AjAkrampoor.Academy.bills.domain.repository.PaymentRepository;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransferEnrollmentUseCase {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseClassRepository classRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final CreateEnrollmentUseCase createEnrollment;
    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;

    public TransferEnrollmentUseCase(EnrollmentRepository enrollmentRepository, CourseClassRepository classRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository, CreateEnrollmentUseCase createEnrollment, BillRepository billRepository, PaymentRepository paymentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.createEnrollment = createEnrollment;
        this.billRepository = billRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public List<Enrollment> execute(UUID enrollmentUUID, String userId, UUID newClassUUID) {
        // 1. Load all existing entities
        Enrollment enrollment = enrollmentRepository.findById(new EnrollmentId(enrollmentUUID))
                .orElseThrow(() -> new IllegalArgumentException("No such enrollment found"));

        if (!enrollment.isEnrolled()) {
            throw new IllegalArgumentException("Enrollment is not active");
        }

        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));

        CourseClass newClass = classRepository.findById(new ClassId(newClassUUID)).orElseThrow(() -> new IllegalArgumentException("No such class found"));
        CourseClass oldClass = classRepository.findById(enrollment.getClassId()).orElseThrow(() -> new IllegalArgumentException("No such class found"));

        // Authorization checks (keep your existing logic)
        if (!userRole.isSuperAdmin()) {
            if (!staff.getBranchId().equals(newClass.getBranchId()) ||
                    !staff.getBranchId().equals(oldClass.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' info");
            }
        }

        if (!newClass.isActive()) {
            throw new IllegalArgumentException("Class is not active");
        }

        // Calculate how much was paid on the old bills
        List<Bill> oldBills = billRepository.findByEnrollmentId(enrollment.getEnrollmentId());

        if (oldBills.isEmpty()) {
            throw new IllegalStateException("No bills found for this enrollment. Cannot transfer.");
        }

        Set<BillId> oldBillIds = oldBills.stream()
                .map(Bill::getBillId)
                .collect(Collectors.toSet());

        Map<BillId, BigDecimal> paidAmountMap = paymentRepository.getPaidAmountsByBillIds(oldBillIds);

        BigDecimal totalPaidOnOldBills = paidAmountMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate the new bill's fee
        BigDecimal newClassFee = newClass.getFee().getAmount();

        // Determine the discount to apply
        // The discount cannot exceed what was paid, and cannot exceed the new fee
        BigDecimal discountToApply = totalPaidOnOldBills.min(newClassFee);

        // Create the new enrollment (this will create a new bill with the discount)
        Enrollment newEnrollment = createEnrollment.execute(
                user.getUserId().toString(),
                enrollment.getStudentId().toString(),
                UUID.fromString(newClass.getId().toString()),
                null,
                discountToApply
        );

        enrollment.transfer(newClass.getId(), user.getUserId());
        Enrollment transferredEnrollment = enrollmentRepository.save(enrollment);

        // Cancel the old bills (keeping payments as revenue)
        for (Bill bill : oldBills) {
            bill.cancel(user.getUserId());
            billRepository.save(bill);
        }

        return List.of(newEnrollment, transferredEnrollment);
    }
}

package com.AjAkrampoor.Academy.bills.application.usecases;

import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.model.BillStatus;
import com.AjAkrampoor.Academy.bills.domain.repository.BillRepository;
import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateBillUseCase {
    private final EnrollmentRepository enrollmentRepository;
    private final BillRepository billRepository;
    private final BranchRepository branchRepository;

    public CreateBillUseCase(EnrollmentRepository enrollmentRepository, BillRepository billRepository, BranchRepository branchRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.billRepository = billRepository;
        this.branchRepository = branchRepository;
    }

    @Transactional
    public Bill execute(UUID enrollmentUUID, BigDecimal moneyAmount, BigDecimal discountAmount, String descriptionStr, UUID branchId) {
        Enrollment enrollment = enrollmentRepository.findById(new EnrollmentId(enrollmentUUID)).orElseThrow(() -> new IllegalArgumentException("No such course found"));
        Branch branch = branchRepository.findById(new BranchId(branchId)).orElseThrow(() -> new IllegalArgumentException("No such branch found"));

        Money amount = new Money(moneyAmount);
        Money discount = new Money(discountAmount);

        Description description = null;
        if (descriptionStr != null && !descriptionStr.trim().isEmpty()) {
            description = new Description(descriptionStr);
        }

        BillId billId;
        int MAX_RETRIES = 10;
        for (int i = 0; i < MAX_RETRIES; i++) {
            billId = BillId.newId();
            if (!billRepository.existsById(billId)) {
                return billRepository.save(new Bill
                        (
                                billId,
                                enrollment.getEnrollmentId(),
                                amount,
                                discount,
                                LocalDateTime.now(),
                                description,
                                branch.getId(),
                                BillStatus.ACTIVE,
                                null, null
                        ));
            }
        }

        throw new IllegalArgumentException("Could not create bill after several retries, please try again later");
    }
}

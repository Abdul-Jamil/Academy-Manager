package com.AjAkrampoor.Academy.salaries.application.usecases.payment;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPayment;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryPaymentRepository;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetSalaryPaymentsUseCase {

    private final SalaryPaymentRepository salaryPaymentRepository;
    private final SalaryAuthorizationService authorizationService;

    public GetSalaryPaymentsUseCase(
            SalaryPaymentRepository salaryPaymentRepository,
            SalaryAuthorizationService authorizationService
    ) {
        this.salaryPaymentRepository = salaryPaymentRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional(readOnly = true)
    public List<SalaryPayment> execute(
            String userId,
            String staffId,
            Integer year,
            Integer month
    ) {
        if (staffId != null && !staffId.isBlank()) {

            StaffId targetStaffId =
                    StaffId.from(staffId);

            authorizationService.requireAccessibleStaff(
                    userId,
                    targetStaffId
            );

            if (year != null && month != null) {

                return salaryPaymentRepository
                        .findByStaffAndMonth(
                                targetStaffId,
                                SalaryMonth.of(
                                        year,
                                        month
                                )
                        );
            }

            return salaryPaymentRepository
                    .findByStaff(targetStaffId);
        }

        if (year != null && month != null) {

            return salaryPaymentRepository
                    .findByMonth(
                            SalaryMonth.of(
                                    year,
                                    month
                            )
                    )
                    .stream()
                    .filter(payment -> {
                        authorizationService.requireAccessibleStaff(
                                userId,
                                payment.getStaffId()
                        );
                        return true;
                    })
                    .toList();
        }

        return salaryPaymentRepository
                .findAll()
                .stream()
                .filter(payment -> {
                    authorizationService.requireAccessibleStaff(
                            userId,
                            payment.getStaffId()
                    );
                    return true;
                })
                .toList();
    }
}
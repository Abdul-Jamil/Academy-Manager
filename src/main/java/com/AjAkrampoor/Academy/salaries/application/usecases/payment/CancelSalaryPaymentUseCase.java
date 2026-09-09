package com.AjAkrampoor.Academy.salaries.application.usecases.payment;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPayment;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentId;
import com.AjAkrampoor.Academy.salaries.domain.repository.MonthlySalaryRepository;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryPaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelSalaryPaymentUseCase {

    private final SalaryPaymentRepository salaryPaymentRepository;
    private final MonthlySalaryRepository monthlySalaryRepository;
    private final SalaryAuthorizationService authorizationService;

    public CancelSalaryPaymentUseCase(
            SalaryPaymentRepository salaryPaymentRepository,
            MonthlySalaryRepository monthlySalaryRepository,
            SalaryAuthorizationService authorizationService) {
        this.salaryPaymentRepository = salaryPaymentRepository;
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public SalaryPayment execute(String userId, String paymentId) {
        SalaryPayment payment = salaryPaymentRepository.findById(SalaryPaymentId.fromString(paymentId))
                .orElseThrow(() -> new IllegalArgumentException("Salary payment not found"));

        authorizationService.requireAccessibleStaff(userId, payment.getStaffId());

        if (payment.isCancelled()) {
            throw new IllegalStateException("Salary payment is already cancelled");
        }

        payment.cancel();

        SalaryPayment saved =
                salaryPaymentRepository.save(payment);

        monthlySalaryRepository
                .findByStaffAndMonth(
                        payment.getStaffId(),
                        payment.getSalaryMonth()
                )
                .ifPresent(monthlySalary -> {

                    monthlySalary.reversePayment(
                            payment.getAmount()
                    );

                    monthlySalaryRepository.save(
                            monthlySalary
                    );
                });

        return saved;
    }
}
package com.AjAkrampoor.Academy.salaries.application.usecases.payment;

import com.AjAkrampoor.Academy.salaries.application.SalaryAuthorizationService;
import com.AjAkrampoor.Academy.salaries.application.SalaryCalculationService;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPayment;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentId;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentStatus;
import com.AjAkrampoor.Academy.salaries.domain.repository.MonthlySalaryRepository;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryPaymentRepository;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class CreateSalaryPaymentUseCase {

    private final SalaryPaymentRepository salaryPaymentRepository;
    private final MonthlySalaryRepository monthlySalaryRepository;
    private final SalaryCalculationService calculationService;
    private final SalaryAuthorizationService authorizationService;

    public CreateSalaryPaymentUseCase(
            SalaryPaymentRepository salaryPaymentRepository,
            MonthlySalaryRepository monthlySalaryRepository,
            SalaryCalculationService calculationService,
            SalaryAuthorizationService authorizationService
    ) {
        this.salaryPaymentRepository = salaryPaymentRepository;
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.calculationService = calculationService;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public SalaryPayment execute(
            String userId,
            String staffId,
            int year,
            int month,
            BigDecimal amount
    ) {
        Staff staff =
                authorizationService.requireAccessibleStaff(
                        userId,
                        StaffId.from(staffId)
                );

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Salary payment amount must be greater than zero"
            );
        }

        SalaryMonth salaryMonth =
                SalaryMonth.of(year, month);

        Money earned =
                calculationService.calculateEarned(
                        staff,
                        salaryMonth,
                        LocalDate.now()
                );

        Money paid =
                calculationService.calculatePaid(
                        staff.getId(),
                        salaryMonth
                );

        Money available =
                earned.subtract(paid);

        Money paymentAmount =
                new Money(amount);

        if (paymentAmount.getAmount().compareTo(
                available.getAmount()
        ) > 0) {
            throw new IllegalArgumentException(
                    "Salary payment cannot exceed currently available salary"
            );
        }

        SalaryPayment payment =
                new SalaryPayment(
                        SalaryPaymentId.newId(),
                        staff.getId(),
                        salaryMonth,
                        paymentAmount,
                        LocalDate.now(),
                        SalaryPaymentStatus.PAID,
                        UserId.fromString(userId)
                );

        SalaryPayment saved =
                salaryPaymentRepository.save(payment);

        monthlySalaryRepository
                .findByStaffAndMonth(
                        staff.getId(),
                        salaryMonth
                )
                .ifPresent(monthlySalary -> {
                    monthlySalary.recordPayment(
                            paymentAmount
                    );

                    monthlySalaryRepository.save(
                            monthlySalary
                    );
                });

        return saved;
    }
}
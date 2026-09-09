package com.AjAkrampoor.Academy.salaries.application;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContract;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractType;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryMonth;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPayment;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryContractRepository;
import com.AjAkrampoor.Academy.salaries.domain.repository.SalaryPaymentRepository;
import com.AjAkrampoor.Academy.sessions.domain.model.Session;
import com.AjAkrampoor.Academy.sessions.domain.model.SessionStatus;
import com.AjAkrampoor.Academy.sessions.domain.repository.SessionRepository;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SalaryCalculationService {

    private final SalaryContractRepository salaryContractRepository;
    private final SalaryPaymentRepository salaryPaymentRepository;
    private final SessionRepository sessionRepository;
    private final StaffUserResolver staffUserResolver;

    public SalaryCalculationService(
            SalaryContractRepository salaryContractRepository,
            SalaryPaymentRepository salaryPaymentRepository,
            SessionRepository sessionRepository,
            StaffUserResolver staffUserResolver
    ) {
        this.salaryContractRepository = salaryContractRepository;
        this.salaryPaymentRepository = salaryPaymentRepository;
        this.sessionRepository = sessionRepository;
        this.staffUserResolver = staffUserResolver;
    }

    public Money calculateEarned(Staff staff, SalaryMonth salaryMonth, LocalDate asOfDate) {
        validateMonth(salaryMonth);

        LocalDate endDate = resolveEarnedEndDate(
                staff,
                salaryMonth,
                asOfDate);

        if (endDate.isBefore(salaryMonth.getStartDate())) {
            return zeroMoney();
        }

        List<SalaryContract> contracts = salaryContractRepository.findByStaffId(staff.getId());

        BigDecimal total = calculateFixedMonthlyEarnings(
                contracts,
                salaryMonth,
                endDate);

        UserId teacherId = staffUserResolver.resolveUserId(staff.getId());

        List<Session> sessions = sessionRepository.findByTeacherIdAndSessionDateBetween(
                teacherId,
                salaryMonth.getStartDate(),
                endDate,
                SessionStatus.HAPPENED);

        for (Session session : sessions) {

            LocalDate sessionDate = session.getSessionDate().getDate();

            SalaryContract contract = findApplicableContract(
                    contracts,
                    sessionDate);

            if (contract == null) {
                continue;
            }

            if (contract.getType() != SalaryContractType.PER_SESSION) {
                continue;
            }

            total = total.add(contract.getRate().getAmount());
        }

        return money(total);
    }

    public Money calculateProjected(Staff staff, SalaryMonth salaryMonth) {
        validateMonth(salaryMonth);

        LocalDate projectionEnd = salaryMonth.getEndDate();

        if (staff.getTerminationDate() != null && staff.getTerminationDate().toLocalDate().isBefore(projectionEnd)) {

            projectionEnd = staff.getTerminationDate().toLocalDate();
        }

        return calculateEarned(staff, salaryMonth, projectionEnd);
    }

    public Money calculatePaid(StaffId staffId, SalaryMonth salaryMonth) {
        return money(salaryPaymentRepository.findByStaffAndMonth(staffId, salaryMonth).stream()
                .filter(SalaryPayment::isPaid)
                .map(payment -> payment.getAmount().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public SalaryCalculation calculate(Staff staff, SalaryMonth salaryMonth) {
        Money earned = calculateEarned(
                staff,
                salaryMonth,
                LocalDate.now());

        Money paid = calculatePaid(
                staff.getId(),
                salaryMonth);

        Money available = earned.subtract(paid);

        Money projected = calculateProjected(
                staff,
                salaryMonth);

        return new SalaryCalculation(
                staff.getId(),
                salaryMonth,
                earned,
                paid,
                available,
                projected
        );
    }

    private BigDecimal calculateFixedMonthlyEarnings(
            List<SalaryContract> contracts,
            SalaryMonth salaryMonth,
            LocalDate endDate) {
        BigDecimal total = BigDecimal.ZERO;

        for (SalaryContract contract : contracts) {

            if (contract.getType() != SalaryContractType.FIXED_MONTHLY) {
                continue;
            }

            LocalDate start = salaryMonth.getStartDate();

            if (contract.getEffectiveFrom().isAfter(start)) {
                start = contract.getEffectiveFrom();
            }

            LocalDate end;

            if (contract.getEffectiveTo() != null && contract.getEffectiveTo().isBefore(endDate)) {
                end = contract.getEffectiveTo();
            } else {
                end = endDate;
            }

            if (end.isBefore(start)) {
                continue;
            }

            long days = ChronoUnit.DAYS.between(start, end) + 1;

            BigDecimal dailyRate = contract.getRate()
                    .getAmount()
                    .divide(BigDecimal.valueOf(salaryMonth.toYearMonth().lengthOfMonth()), 12, RoundingMode.HALF_UP);

            total = total.add(dailyRate.multiply(BigDecimal.valueOf(days)));
        }

        return total;
    }

    private SalaryContract findApplicableContract(
            List<SalaryContract> contracts,
            LocalDate date) {
        return contracts.stream()
                .filter(contract -> contract.appliesOn(date))
                .findFirst()
                .orElse(null);
    }

    private LocalDate resolveEarnedEndDate(
            Staff staff,
            SalaryMonth salaryMonth,
            LocalDate asOfDate) {
        LocalDate today = LocalDate.now();

        LocalDate endDate = salaryMonth.getEndDate();

        if (!salaryMonth.isBefore(SalaryMonth.current())) {
            endDate = today;
        }

        if (asOfDate != null && asOfDate.isBefore(endDate)) {
            endDate = asOfDate;
        }

        if (staff.getTerminationDate() != null && staff.getTerminationDate().toLocalDate().isBefore(endDate)) {

            endDate = staff.getTerminationDate().toLocalDate();
        }

        return endDate;
    }

    private void validateMonth(SalaryMonth salaryMonth) {
        if (salaryMonth.isAfter(SalaryMonth.current())) {
            throw new IllegalArgumentException("Cannot calculate salary for a future month");
        }
    }

    private Money money(BigDecimal amount) {
        return new Money(amount.setScale(2, RoundingMode.HALF_UP));
    }

    private Money zeroMoney() {
        return new Money(BigDecimal.ZERO);
    }
}
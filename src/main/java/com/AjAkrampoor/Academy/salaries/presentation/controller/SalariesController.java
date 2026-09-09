package com.AjAkrampoor.Academy.salaries.presentation.controller;

import com.AjAkrampoor.Academy.salaries.application.MonthlySalaryResponseAssembler;
import com.AjAkrampoor.Academy.salaries.application.SalaryCalculationResponseAssembler;
import com.AjAkrampoor.Academy.salaries.application.SalaryContractResponseAssembler;
import com.AjAkrampoor.Academy.salaries.application.SalaryPaymentResponseAssembler;
import com.AjAkrampoor.Academy.salaries.application.dto.*;
import com.AjAkrampoor.Academy.salaries.application.usecases.contract.ChangeSalaryContractUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.contract.CreateSalaryContractUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.contract.GetActiveSalaryContractsUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.contract.GetSalaryContractHistoryUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.monthly.FinalizeMonthlySalaryUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.monthly.GetMonthlySalariesUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.monthly.GetMonthlySalaryUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.monthly.GetStaffSalaryHistoryUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.payment.CancelSalaryPaymentUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.payment.CreateSalaryPaymentUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.payment.GetSalaryPaymentsUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.salary.GetAvailableSalaryToPayUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.salary.GetCurrentMonthSalariesUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.salary.GetCurrentMonthSalaryUseCase;
import com.AjAkrampoor.Academy.salaries.application.usecases.salary.GetEarnedSalaryUseCase;
import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalary;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContract;
import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPayment;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalariesController {

    private final CreateSalaryContractUseCase createSalaryContract;
    private final ChangeSalaryContractUseCase changeSalaryContract;
    private final GetActiveSalaryContractsUseCase getActiveSalaryContracts;
    private final GetSalaryContractHistoryUseCase getSalaryContractHistory;

    private final GetCurrentMonthSalaryUseCase getCurrentMonthSalary;
    private final GetCurrentMonthSalariesUseCase getCurrentMonthSalaries;
    private final GetEarnedSalaryUseCase getEarnedSalary;
    private final GetAvailableSalaryToPayUseCase getAvailableSalaryToPay;

    private final CreateSalaryPaymentUseCase createSalaryPayment;
    private final GetSalaryPaymentsUseCase getSalaryPayments;
    private final CancelSalaryPaymentUseCase cancelPayment;

    private final FinalizeMonthlySalaryUseCase finalizeMonthlySalary;
    private final GetMonthlySalaryUseCase getMonthlySalary;
    private final GetMonthlySalariesUseCase getMonthlySalaries;
    private final GetStaffSalaryHistoryUseCase getStaffSalaryHistory;

    private final SalaryContractResponseAssembler salaryContractResponseAssembler;
    private final SalaryCalculationResponseAssembler salaryCalculationResponseAssembler;
    private final SalaryPaymentResponseAssembler salaryPaymentResponseAssembler;
    private final MonthlySalaryResponseAssembler monthlySalaryResponseAssembler;

    public SalariesController(
            CreateSalaryContractUseCase createSalaryContract,
            ChangeSalaryContractUseCase changeSalaryContract,
            GetActiveSalaryContractsUseCase getActiveSalaryContracts,
            GetSalaryContractHistoryUseCase getSalaryContractHistory,
            GetCurrentMonthSalaryUseCase getCurrentMonthSalary,
            GetCurrentMonthSalariesUseCase getCurrentMonthSalaries,
            GetEarnedSalaryUseCase getEarnedSalary,
            GetAvailableSalaryToPayUseCase getAvailableSalaryToPay,
            CreateSalaryPaymentUseCase createSalaryPayment,
            GetSalaryPaymentsUseCase getSalaryPayments,
            CancelSalaryPaymentUseCase cancelPayment,
            FinalizeMonthlySalaryUseCase finalizeMonthlySalary,
            GetMonthlySalaryUseCase getMonthlySalary,
            GetMonthlySalariesUseCase getMonthlySalaries,
            GetStaffSalaryHistoryUseCase getStaffSalaryHistory,
            SalaryContractResponseAssembler salaryContractResponseAssembler,
            SalaryCalculationResponseAssembler salaryCalculationResponseAssembler,
            SalaryPaymentResponseAssembler salaryPaymentResponseAssembler,
            MonthlySalaryResponseAssembler monthlySalaryResponseAssembler
    ) {
        this.createSalaryContract = createSalaryContract;
        this.changeSalaryContract = changeSalaryContract;
        this.getActiveSalaryContracts = getActiveSalaryContracts;
        this.getSalaryContractHistory = getSalaryContractHistory;
        this.getCurrentMonthSalary = getCurrentMonthSalary;
        this.getCurrentMonthSalaries = getCurrentMonthSalaries;
        this.getEarnedSalary = getEarnedSalary;
        this.getAvailableSalaryToPay = getAvailableSalaryToPay;
        this.createSalaryPayment = createSalaryPayment;
        this.getSalaryPayments = getSalaryPayments;
        this.cancelPayment = cancelPayment;
        this.finalizeMonthlySalary = finalizeMonthlySalary;
        this.getMonthlySalary = getMonthlySalary;
        this.getMonthlySalaries = getMonthlySalaries;
        this.getStaffSalaryHistory = getStaffSalaryHistory;
        this.salaryContractResponseAssembler = salaryContractResponseAssembler;
        this.salaryCalculationResponseAssembler = salaryCalculationResponseAssembler;
        this.salaryPaymentResponseAssembler = salaryPaymentResponseAssembler;
        this.monthlySalaryResponseAssembler = monthlySalaryResponseAssembler;
    }

    @PostMapping("/contracts")
    @PreAuthorize("hasAuthority('SALARY_CONTRACT_CREATE')")
    public ResponseEntity<SalaryContractResponse> createSalaryContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateSalaryContractRequest request
    ) {
        SalaryContract contract =
                createSalaryContract.execute(
                        userDetails.getUserId(),
                        request.getStaffId(),
                        request.getType(),
                        request.getRate(),
                        request.getEffectiveFrom()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        salaryContractResponseAssembler.toResponse(
                                contract
                        )
                );
    }

    @PutMapping("/contracts/change")
    @PreAuthorize("hasAuthority('SALARY_CONTRACT_CHANGE')")
    public ResponseEntity<SalaryContractResponse> changeSalaryContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ChangeSalaryContractRequest request
    ) {
        SalaryContract contract =
                changeSalaryContract.execute(
                        userDetails.getUserId(),
                        request.getStaffId(),
                        request.getType(),
                        request.getRate(),
                        request.getEffectiveFrom()
                );

        return ResponseEntity.ok(
                salaryContractResponseAssembler.toResponse(
                        contract
                )
        );
    }

    @GetMapping("/contracts/active")
    @PreAuthorize("hasAuthority('SALARY_CONTRACT_READ')")
    public List<SalaryContractResponse> getActiveSalaryContracts(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String staffId
    ) {
        return getActiveSalaryContracts
                .execute(
                        userDetails.getUserId(),
                        staffId
                )
                .stream()
                .map(salaryContractResponseAssembler::toResponse)
                .toList();
    }

    @GetMapping("/contracts/history/{staffId}")
    @PreAuthorize("hasAuthority('SALARY_CONTRACT_READ')")
    public List<SalaryContractResponse> getSalaryContractHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String staffId
    ) {
        return getSalaryContractHistory
                .execute(
                        userDetails.getUserId(),
                        staffId
                )
                .stream()
                .map(salaryContractResponseAssembler::toResponse)
                .toList();
    }

    @GetMapping("/current/{staffId}")
    @PreAuthorize("hasAuthority('SALARY_READ')")
    public SalaryCalculationResponse getCurrentMonthSalary(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String staffId
    ) {
        return salaryCalculationResponseAssembler.toResponse(
                getCurrentMonthSalary.execute(
                        userDetails.getUserId(),
                        staffId
                )
        );
    }

    @GetMapping("/current")
    @PreAuthorize("hasAuthority('SALARY_READ')")
    public List<SalaryCalculationResponse> getCurrentMonthSalaries(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return getCurrentMonthSalaries
                .execute(userDetails.getUserId())
                .stream()
                .map(salaryCalculationResponseAssembler::toResponse)
                .toList();
    }

    @GetMapping("/earned/{staffId}")
    @PreAuthorize("hasAuthority('SALARY_READ')")
    public BigDecimal getEarnedSalary(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String staffId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return getEarnedSalary
                .execute(
                        userDetails.getUserId(),
                        staffId,
                        year,
                        month
                )
                .getAmount();
    }

    @GetMapping("/available/{staffId}")
    @PreAuthorize("hasAuthority('SALARY_READ')")
    public BigDecimal getAvailableSalaryToPay(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String staffId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return getAvailableSalaryToPay
                .execute(
                        userDetails.getUserId(),
                        staffId,
                        year,
                        month
                )
                .getAmount();
    }

    @PostMapping("/payments")
    @PreAuthorize("hasAuthority('SALARY_PAYMENT_CREATE')")
    public ResponseEntity<SalaryPaymentResponse> createSalaryPayment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateSalaryPaymentRequest request
    ) {
        SalaryPayment payment =
                createSalaryPayment.execute(
                        userDetails.getUserId(),
                        request.getStaffId(),
                        request.getYear(),
                        request.getMonth(),
                        request.getAmount()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        salaryPaymentResponseAssembler.toResponse(
                                payment
                        )
                );
    }

    @GetMapping("/payments")
    @PreAuthorize("hasAuthority('SALARY_PAYMENT_READ')")
    public List<SalaryPaymentResponse> getSalaryPayments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String staffId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        return getSalaryPayments
                .execute(
                        userDetails.getUserId(),
                        staffId,
                        year,
                        month
                )
                .stream()
                .map(salaryPaymentResponseAssembler::toResponse)
                .toList();
    }

    @PatchMapping("/payments/{paymentId}/cancel")
    @PreAuthorize("hasAuthority('SALARY_PAYMENT_CANCEL')")
    public SalaryPaymentResponse cancelPayment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String paymentId
    ) {
        return salaryPaymentResponseAssembler.toResponse(
                cancelPayment.execute(
                        userDetails.getUserId(),
                        paymentId
                )
        );
    }

    @PostMapping("/monthly/{staffId}/finalize")
    @PreAuthorize("hasAuthority('SALARY_MONTHLY_FINALIZE')")
    public ResponseEntity<MonthlySalaryResponse> finalizeMonthlySalary(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String staffId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        MonthlySalary monthlySalary =
                finalizeMonthlySalary.execute(
                        userDetails.getUserId(),
                        staffId,
                        year,
                        month
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        monthlySalaryResponseAssembler.toResponse(
                                monthlySalary
                        )
                );
    }

    @GetMapping("/monthly/{staffId}")
    @PreAuthorize("hasAuthority('SALARY_MONTHLY_READ')")
    public MonthlySalaryResponse getMonthlySalary(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String staffId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return monthlySalaryResponseAssembler.toResponse(
                getMonthlySalary.execute(
                        userDetails.getUserId(),
                        staffId,
                        year,
                        month
                )
        );
    }

    @GetMapping("/monthly")
    @PreAuthorize("hasAuthority('SALARY_MONTHLY_READ')")
    public List<MonthlySalaryResponse> getMonthlySalaries(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return getMonthlySalaries
                .execute(
                        userDetails.getUserId(),
                        year,
                        month
                )
                .stream()
                .map(monthlySalaryResponseAssembler::toResponse)
                .toList();
    }

    @GetMapping("/history/{staffId}")
    @PreAuthorize("hasAuthority('SALARY_MONTHLY_READ')")
    public List<MonthlySalaryResponse> getStaffSalaryHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String staffId
    ) {
        return getStaffSalaryHistory
                .execute(
                        userDetails.getUserId(),
                        staffId
                )
                .stream()
                .map(monthlySalaryResponseAssembler::toResponse)
                .toList();
    }
}
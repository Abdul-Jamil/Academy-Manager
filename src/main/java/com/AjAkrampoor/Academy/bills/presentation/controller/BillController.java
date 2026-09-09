package com.AjAkrampoor.Academy.bills.presentation.controller;

import com.AjAkrampoor.Academy.bills.application.BillQueryService;
import com.AjAkrampoor.Academy.bills.application.PaymentResponseAssembler;
import com.AjAkrampoor.Academy.bills.application.dto.BillFilter;
import com.AjAkrampoor.Academy.bills.application.dto.BillResponse;
import com.AjAkrampoor.Academy.bills.application.dto.PaymentFilter;
import com.AjAkrampoor.Academy.bills.application.dto.PaymentResponse;
import com.AjAkrampoor.Academy.bills.application.usecases.*;
import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.model.Payment;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/bills")
public class BillController {

    private final GetBillsUseCase getBills;
    private final UpdateBillDescriptionUseCase updateBillDescription;

    private final GetPaymentsUseCase getPayments;
    private final CreatePaymentUseCase createPayment;
    private final UpdatePaymentDescriptionUseCase updatePaymentDescription;
    private final CancelPaymentUseCase cancelPayment;

    private final BillQueryService billQueryService;
    private final PaymentResponseAssembler paymentResponseAssembler;

    public BillController(GetBillsUseCase getBillsUseCase, UpdateBillDescriptionUseCase updateBillDescription, GetPaymentsUseCase getPayments, CreatePaymentUseCase createPayment, UpdatePaymentDescriptionUseCase updatePaymentDescription, CancelPaymentUseCase cancelPayment, BillQueryService billQueryService, PaymentResponseAssembler paymentResponseAssembler) {
        this.getBills = getBillsUseCase;
        this.updateBillDescription = updateBillDescription;
        this.getPayments = getPayments;
        this.createPayment = createPayment;
        this.updatePaymentDescription = updatePaymentDescription;
        this.cancelPayment = cancelPayment;
        this.billQueryService = billQueryService;
        this.paymentResponseAssembler = paymentResponseAssembler;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('BILL_READ')")
    public ResponseEntity<PaginatedResponse<BillResponse>> getBills(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @ModelAttribute BillFilter filter) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);

        PaginatedResponse<Bill> billsPage = getBills.execute(userDetails.getUserId(), paginationRequest, filter);

        // Extract bill IDs and get a fast lookup map
        Set<BillId> billIds = billsPage.getContent().stream()
                .map(Bill::getBillId)
                .collect(Collectors.toSet());
        Map<BillId, BillResponse> responseMap = billQueryService.getBillResponseMap(billIds);

        // Transform the page
        PaginatedResponse<BillResponse> responsePage = billsPage.map(bill -> responseMap.get(bill.getBillId()));

        return ResponseEntity.ok(responsePage);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('BILL_UPDATE_DESCRIPTION')")
    public BillResponse updateBillDescription(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID id,
            @RequestParam String description) {


        updateBillDescription.execute(userDetails.getUserId(), id, description);
        return billQueryService.getBillResponse(id);
    }

    @GetMapping("/payment")
    @PreAuthorize("hasAuthority('BILL_READ_PAYMENTS')")
    public PaginatedResponse<PaymentResponse> getPayments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @ModelAttribute PaymentFilter filter) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Payment> payments = getPayments.execute(userDetails.getUserId(), filter, paginationRequest);

        return payments.map(paymentResponseAssembler::toResponse);
    }

    @PostMapping("/{id}/payment")
    @PreAuthorize("hasAuthority('BILL_CREATE_PAYMENT')")
    public PaymentResponse createPayment(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id, @RequestParam BigDecimal amount, @RequestParam(required = false) String description) {
        Payment payment = createPayment.execute(userDetails.getUserId(), id, amount, description);
        return paymentResponseAssembler.toResponse(payment);
    }

    @PatchMapping("/payment/{id}")
    @PreAuthorize("hasAuthority('BILL_UPDATE_PAYMENT_DESC')")
    public PaymentResponse updatePaymentDescription(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id, @RequestParam String description) {
        Payment payment = updatePaymentDescription.execute(userDetails.getUserId(), id, description);
        return paymentResponseAssembler.toResponse(payment);
    }

    @PatchMapping("/payment/{id}/delete")
    @PreAuthorize("hasAuthority('BILL_PAYMENT_DELETE')")
    public PaymentResponse cancelPayment(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id) {
        Payment payment = cancelPayment.execute(userDetails.getUserId(), id);
        return paymentResponseAssembler.toResponse(payment);
    }

}



package com.AjAkrampoor.Academy.bills.application;

import com.AjAkrampoor.Academy.bills.application.dto.BillResponse;
import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.repository.BillRepository;
import com.AjAkrampoor.Academy.bills.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BillQueryService {

    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;
    private final BillResponseAssembler assembler;

    public BillQueryService(BillRepository billRepository,
                            PaymentRepository paymentRepository,
                            BillResponseAssembler assembler) {
        this.billRepository = billRepository;
        this.paymentRepository = paymentRepository;
        this.assembler = assembler;
    }

    public BillResponse getBillResponse(UUID billUuid) {
        BillId billId = new BillId(billUuid);
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));

        Map<BillId, BigDecimal> paidAmountMap =
                paymentRepository.getPaidAmountsByBillIds(Set.of(billId));

        BigDecimal paidAmount = paidAmountMap.getOrDefault(billId, BigDecimal.ZERO);
        return assembler.toResponse(bill, paidAmount);
    }

    public Map<BillId, BillResponse> getBillResponseMap(Set<BillId> billIds) {
        // Fetch all bills in one query
        List<Bill> bills = billRepository.findAllById(billIds);
        Map<BillId, Bill> billMap = bills.stream()
                .collect(Collectors.toMap(Bill::getBillId, Function.identity()));

        Set<BillId> missingIds = billIds.stream()
                .filter(id -> !billMap.containsKey(id))
                .collect(Collectors.toSet());
        if (!missingIds.isEmpty()) {
            throw new IllegalArgumentException("Bills not found for IDs: " + missingIds);
        }

        // Batch paid amounts
        Map<BillId, BigDecimal> paidAmountMap =
                paymentRepository.getPaidAmountsByBillIds(billIds);

        return billIds.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        id -> assembler.toResponse(
                                billMap.get(id),
                                paidAmountMap.getOrDefault(id, BigDecimal.ZERO)
                        )
                ));
    }
}
package com.AjAkrampoor.Academy.bills.application;

import com.AjAkrampoor.Academy.bills.application.dto.PaymentResponse;
import com.AjAkrampoor.Academy.bills.domain.model.Payment;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class PaymentResponseAssembler {
    private final UserRepository userRepository;

    public PaymentResponseAssembler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PaymentResponse toResponse(Payment payment) {

        User received = userRepository.findById(payment.getReceivedBy()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        String cancelled = null;
        if (payment.getCancelledBy() != null) {
            cancelled = userRepository.findById(payment.getCancelledBy()).orElseThrow(() -> new IllegalArgumentException("User not found")).getUserName().toString();
        }

        String description = (payment.getDescription() == null) ? null : payment.getDescription().getValue();
        return new PaymentResponse
                (
                        payment.getPaymentId().toString(),
                        payment.getBillId().toString(),
                        received.getUserName().toString(),
                        payment.getAmount().getAmount(),
                        description,
                        payment.getDate(),
                        payment.getCancelledAt(),
                        cancelled,
                        payment.getStatus()
                );
    }
}

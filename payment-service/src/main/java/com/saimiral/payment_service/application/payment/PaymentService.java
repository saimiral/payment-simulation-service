package com.saimiral.payment_service.application.payment;

import com.saimiral.payment_service.domain.payment.PaymentEvent;
import com.saimiral.payment_service.domain.payment.PaymentIntent;
import com.saimiral.payment_service.infrastructure.persistence.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public UUID createPayment(String merchantId, Long amount, String currency){
        PaymentIntent newPayment = PaymentIntent.createNew(merchantId, amount, currency);
        paymentRepository.save(newPayment);

        return newPayment.getId();
    }

    public void applyEvent(UUID paymentId, PaymentEvent event){
        PaymentIntent payment = paymentRepository
                .findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found. ID: " + paymentId));
        payment.apply(event);
        paymentRepository.save(payment);
    }
}

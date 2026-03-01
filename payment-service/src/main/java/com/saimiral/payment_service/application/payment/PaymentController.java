package com.saimiral.payment_service.application.payment;

import com.saimiral.payment_service.api.payment.dto.CreatePaymentRequest;
import com.saimiral.payment_service.api.payment.dto.PaymentResponse;
import com.saimiral.payment_service.domain.payment.PaymentIntent;
import com.saimiral.payment_service.domain.payment.PaymentStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody CreatePaymentRequest request){
        PaymentIntent payment = paymentService.createPayment(
                request.getMerchantId(),
                request.getAmount(),
                request.getCurrency()
        );

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                payment.getMerchantId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getStatus() == PaymentStatus.FAILED ? payment.getFailureCode() : null,
                payment.getStatus() == PaymentStatus.FAILED ? payment.getFailureMessage() : null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

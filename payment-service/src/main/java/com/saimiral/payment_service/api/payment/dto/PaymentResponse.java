package com.saimiral.payment_service.api.payment.dto;

import com.saimiral.payment_service.domain.payment.PaymentStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PaymentResponse {
    private final UUID id;
    private final String merchantId;
    private final Long amount;
    private final String currency;
    private final PaymentStatus status;
    private final LocalDateTime updatedAt;
    private final String failureCode;
    private final String failureMessage;

    public PaymentResponse(UUID id, String merchantId, Long amount, String currency, PaymentStatus status, LocalDateTime updatedAt, String failureCode, String failureMessage) {
        this.id = id;
        this.merchantId = merchantId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.updatedAt = updatedAt;
        this.failureCode = failureCode;
        this.failureMessage = failureMessage;
    }
}

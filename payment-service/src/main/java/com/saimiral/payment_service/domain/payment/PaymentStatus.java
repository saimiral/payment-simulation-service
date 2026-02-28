package com.saimiral.payment_service.domain.payment;

public enum PaymentStatus {
    CREATED,
    PROCESSING,
    AUTHORIZED,
    CAPTURED,
    FAILED,
    CANCELLED
}

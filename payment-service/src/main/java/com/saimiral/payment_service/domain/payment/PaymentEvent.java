package com.saimiral.payment_service.domain.payment;

public enum PaymentEvent {
    CONFIRM,
    AUTH_SUCCESS,
    AUTH_FAIL,
    CAPTURE,
    CANCEL
}

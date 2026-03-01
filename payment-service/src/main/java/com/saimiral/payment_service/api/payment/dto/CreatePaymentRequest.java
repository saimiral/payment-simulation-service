package com.saimiral.payment_service.api.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePaymentRequest {
    @NotBlank
    private String merchantId;

    @NotNull
    @Positive
    private Long amount;

    @NotBlank
    private String currency;
}

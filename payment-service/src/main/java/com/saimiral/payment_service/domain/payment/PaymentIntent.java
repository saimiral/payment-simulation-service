package com.saimiral.payment_service.domain.payment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentIntent {
    @Id
    private UUID id;
    private String merchantId;
    private Long amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String failureCode;
    private String failureMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Version
    private Long version;

    private static final Map<PaymentStatus, Map<PaymentEvent, PaymentStatus>> TRANSITIONS =
            Map.of(
                    PaymentStatus.CREATED, Map.of(
                            PaymentEvent.CONFIRM, PaymentStatus.PROCESSING,
                            PaymentEvent.CANCEL, PaymentStatus.CANCELLED
                    ),
                    PaymentStatus.PROCESSING, Map.of(
                            PaymentEvent.AUTH_SUCCESS, PaymentStatus.AUTHORIZED,
                            PaymentEvent.AUTH_FAIL, PaymentStatus.FAILED,
                            PaymentEvent.CANCEL, PaymentStatus.CANCELLED
                    ),
                    PaymentStatus.AUTHORIZED, Map.of(
                            PaymentEvent.CAPTURE, PaymentStatus.CAPTURED
                    )
            );

    public static PaymentIntent createNew(String merchantId, Long amount, String currency){
        PaymentIntent payment = new PaymentIntent();
        payment.id = UUID.randomUUID();
        payment.merchantId = merchantId;
        payment.amount = amount;
        payment.currency = currency;
        payment.status = PaymentStatus.CREATED;
        payment.createdAt = LocalDateTime.now();
        payment.updatedAt = LocalDateTime.now();

        return payment;
    }
    public void apply(PaymentEvent event){
        Map<PaymentEvent, PaymentStatus> eventMap = TRANSITIONS.get(this.status);
        if(eventMap == null) throw new IllegalStateException("No events found for status: " + status);
        PaymentStatus newStatus = eventMap.get(event);
        if(newStatus == null) throw new IllegalStateException("Status: " + status + " does not allow event: " + event);
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }
}

package com.saimiral.payment_service.infrastructure.persistence;

import com.saimiral.payment_service.domain.payment.PaymentIntent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentIntent, UUID> {

}

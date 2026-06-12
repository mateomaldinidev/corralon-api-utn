package com.utn.corralon.features.payment.controller;

import com.utn.corralon.features.payment.dto.PaymentRequestDTO;
import com.utn.corralon.features.payment.dto.PaymentResponseDTO;
import com.utn.corralon.features.payment.service.IPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    @PostMapping("/pay")
    @PreAuthorize("hasAuthority('PAYMENT_CREATE')")
    public ResponseEntity<PaymentResponseDTO> pay(
            @Valid @RequestBody PaymentRequestDTO request
    ) {

        return ResponseEntity.ok(
                paymentService.pay(request)
        );
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAuthority('PAYMENT_READ')")
    public ResponseEntity<PaymentResponseDTO> getByOrder(
            @PathVariable UUID orderId
    ) {
        return ResponseEntity.ok(
                paymentService.getByOrderId(orderId)
        );
    }
}
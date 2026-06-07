package com.utn.corralon.features.payment.controller;

import com.utn.corralon.features.payment.dto.PaymentRequestDTO;
import com.utn.corralon.features.payment.dto.PaymentResponseDTO;
import com.utn.corralon.features.payment.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponseDTO> pay(
            @RequestBody PaymentRequestDTO request
    ) {

        return ResponseEntity.ok(
                paymentService.pay(request)
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getByOrder(
            @PathVariable UUID orderId
    ) {
        return ResponseEntity.ok(
                paymentService.getByOrderId(orderId)
        );
    }
}
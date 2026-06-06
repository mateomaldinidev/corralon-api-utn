package com.utn.corralon.features.order.controller;

import com.utn.corralon.features.order.dto.CreateOrderRequestDTO;
import com.utn.corralon.features.order.dto.OrderAdminResponseDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.dto.OrderSummaryDTO;
import com.utn.corralon.features.order.service.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody CreateOrderRequestDTO dto, @RequestParam UUID userExternalId) {

        OrderResponseDTO response = orderService.createOrder(dto, userExternalId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderAdminResponseDTO>> getAll() {

        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<OrderResponseDTO> getByExternalId(@PathVariable UUID externalId) {

        return ResponseEntity.ok(
                orderService.getByExternalId(externalId)
        );
    }

    @PatchMapping("/{externalId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID externalId) {

        orderService.cancelOrder(externalId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userExternalId}")
    public ResponseEntity<List<OrderSummaryDTO>> getOrdersByUser(
            @PathVariable UUID userExternalId) {

        return ResponseEntity.ok(orderService.getOrdersByUser(userExternalId));
    }

    @GetMapping("/admin/{externalId}")
    public ResponseEntity<OrderAdminResponseDTO> getAdminOrder(
            @PathVariable UUID externalId) {

        return ResponseEntity.ok(orderService.getAdminOrder(externalId));
    }
}

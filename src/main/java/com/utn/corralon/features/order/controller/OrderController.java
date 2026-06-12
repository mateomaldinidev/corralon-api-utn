package com.utn.corralon.features.order.controller;

import com.utn.corralon.features.order.dto.OrderAdminResponseDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.dto.OrderSummaryDTO;
import com.utn.corralon.features.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PreAuthorize("hasAuthority('ORDER_READ_ALL')")
    @GetMapping("/admin")
    public ResponseEntity<List<OrderAdminResponseDTO>> getAll() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getAll());
    }

    @PreAuthorize("hasAuthority('ORDER_READ')")
    @GetMapping("/{externalId}")
    public ResponseEntity<OrderResponseDTO> getByExternalId(
            @PathVariable UUID externalId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getByExternalId(externalId));
    }



    @PostMapping("/{externalId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable UUID externalId,
            @RequestParam UUID userExternalId
    ) {

        orderService.cancelOrder(externalId, userExternalId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PreAuthorize("hasAuthority('ORDER_READ_BY_USER')")
    @GetMapping("/user/{userExternalId}")
    public ResponseEntity<List<OrderSummaryDTO>> getOrdersByUser(
            @PathVariable UUID userExternalId) {

        return ResponseEntity.ok(orderService.getOrdersByUser(userExternalId));
    }

    @PreAuthorize("hasAuthority('ORDER_READ_ALL')")
    @GetMapping("/admin/{externalId}")
    public ResponseEntity<OrderAdminResponseDTO> getAdminOrder(
            @PathVariable UUID externalId) {

        return ResponseEntity.ok(orderService.getAdminOrder(externalId));
    }
}

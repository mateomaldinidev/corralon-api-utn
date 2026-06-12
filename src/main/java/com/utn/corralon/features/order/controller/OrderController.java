package com.utn.corralon.features.order.controller;

import com.utn.corralon.features.order.dto.OrderAdminResponseDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.dto.OrderSummaryDTO;
import com.utn.corralon.features.order.service.IOrderService;
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

    @GetMapping("/admin") //solo para admin porque devuelve informaciòn administrativa
    public ResponseEntity<List<OrderAdminResponseDTO>> getAll() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getAll());
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<OrderResponseDTO> getByExternalId(
            @PathVariable UUID externalId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getByExternalId(externalId));
    }



    @PostMapping("/{externalId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable UUID externalId
    ) {

        orderService.cancelOrder(externalId);

        return ResponseEntity
                .noContent()
                .build();
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

package com.utn.corralon.features.order.controller;

import com.utn.corralon.features.order.dto.OrderRequestDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.service.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getAll());
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<OrderResponseDTO> getByExternalId(
            @PathVariable UUID externalId
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getByExternalId(externalId));
    }


    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> cancel(
            @PathVariable UUID externalId
    ) {

        orderService.delete(externalId);

        return ResponseEntity
                .noContent()
                .build();
    }
}

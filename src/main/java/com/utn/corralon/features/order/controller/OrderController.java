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

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(
            @Valid @RequestBody OrderRequestDTO dto
    ) {

        OrderResponseDTO response = orderService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {

        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<OrderResponseDTO> getByExternalId(
            @PathVariable UUID externalId
    ) {

        return ResponseEntity.ok(
                orderService.getByExternalId(externalId)
        );
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<OrderResponseDTO> update(
            @PathVariable UUID externalId,
            @Valid @RequestBody OrderRequestDTO dto
    ) {

        return ResponseEntity.ok(
                orderService.update(externalId, dto)
        );
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID externalId
    ) {

        orderService.delete(externalId);

        return ResponseEntity.noContent().build();
    }
}

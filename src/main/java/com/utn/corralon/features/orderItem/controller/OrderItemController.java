package com.utn.corralon.features.orderItem.controller;

import com.utn.corralon.features.orderItem.dto.OrderItemRequestDTO;
import com.utn.corralon.features.orderItem.dto.OrderItemResponseDTO;
import com.utn.corralon.features.orderItem.service.IOrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final IOrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> create(
            @Valid @RequestBody OrderItemRequestDTO dto
    ) {

        OrderItemResponseDTO response =
                orderItemService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderItemResponseDTO>> getAll() {

        return ResponseEntity.ok(
                orderItemService.getAll()
        );
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<OrderItemResponseDTO> getByExternalId(
            @PathVariable UUID externalId
    ) {

        return ResponseEntity.ok(
                orderItemService.getByExternalId(externalId)
        );
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<OrderItemResponseDTO> update(
            @PathVariable UUID externalId,
            @Valid @RequestBody OrderItemRequestDTO dto
    ) {

        return ResponseEntity.ok(
                orderItemService.update(externalId, dto)
        );
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID externalId
    ) {

        orderItemService.delete(externalId);

        return ResponseEntity.noContent().build();
    }
}
package com.utn.corralon.features.orders;

import com.utn.corralon.features.orders.dto.OrderRequestDTO;
import com.utn.corralon.features.orders.dto.OrderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO create(
            @Valid @RequestBody OrderRequestDTO request
    ) {

        return orderService.create(request);
    }

    @GetMapping
    public List<OrderResponseDTO> findAll() {

        return orderService.findAll();
    }

    @GetMapping("/{externalId}")
    public OrderResponseDTO findByExternalId(
            @PathVariable UUID externalId
    ) {

        return orderService.findByExternalId(externalId);
    }

    @PutMapping("/{externalId}")
    public OrderResponseDTO update(
            @PathVariable UUID externalId,
            @Valid @RequestBody OrderRequestDTO request
    ) {

        return orderService.update(
                externalId,
                request
        );
    }

    @DeleteMapping("/{externalId}")
    public void delete(
            @PathVariable UUID externalId
    ) {

        orderService.delete(externalId);
    }
}

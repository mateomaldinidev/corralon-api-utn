package com.utn.corralon.features.stockMovement.dto;

import com.utn.corralon.features.stockMovement.enums.StockMovementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMovementRequestDTO {

    @NotNull(message = "The quantity cannot be null.")
    private Integer quantity;

    @NotBlank
    @Size(min = 10, max = 500, message = "The reason lenth is min = 10, max = 500.")
    private String reason;

    @NotNull(message = "The variantId cannot be null.")
    private UUID variantId;
}

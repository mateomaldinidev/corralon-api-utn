package com.utn.corralon.features.cart.dto;

import com.utn.corralon.features.cart_item.dto.CartItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO {
   private UUID externalId;
   private UUID userExternalId;
   private LocalDateTime lastUpdated;
   private BigDecimal totalAmount;
   private List<CartItemResponseDTO> cartItems;

}

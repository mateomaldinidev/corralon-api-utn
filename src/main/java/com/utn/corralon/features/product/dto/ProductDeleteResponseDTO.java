package com.utn.corralon.features.product.dto;

import com.utn.corralon.features.productVariant.dto.DisabledVariantDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductDeleteResponseDTO {
    private UUID productExternalId;
    private String productName;
    private Boolean active;
    private List<DisabledVariantDTO> disabledVariants;

}

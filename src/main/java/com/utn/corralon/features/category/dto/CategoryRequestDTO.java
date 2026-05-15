package com.utn.corralon.features.category.dto;
import jakarta.validation.constraints.NotBlank;
import lombok. *;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDTO {
    @NotBlank
    private String name;
    @NotBlank
    private boolean active;
}

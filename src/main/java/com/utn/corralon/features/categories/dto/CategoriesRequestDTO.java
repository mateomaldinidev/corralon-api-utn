package com.utn.corralon.features.categories.dto;
import jakarta.validation.constraints.NotBlank;
import lombok. *;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriesRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private Boolean active;
}

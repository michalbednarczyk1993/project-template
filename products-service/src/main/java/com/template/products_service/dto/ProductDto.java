package com.template.products_service.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductDto {
    private UUID id;

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;

    private UUID categoryId;

    private List<String> imageUrls;
}
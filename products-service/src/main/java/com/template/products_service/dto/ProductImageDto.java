package com.template.products_service.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ProductImageDto {
    private UUID id;

    @NotNull
    private UUID productId;

    @NotBlank
    private String imageUrl;
}
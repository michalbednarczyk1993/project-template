package com.template.products_service.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class CategoryDto {
    private UUID id;

    @NotBlank
    private String name;
}
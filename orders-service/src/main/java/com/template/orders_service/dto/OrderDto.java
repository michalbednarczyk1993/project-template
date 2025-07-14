package com.template.orders_service.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
public class OrderDto {
    private UUID id;
    @NotNull
    private UUID userId;
    @NotNull
    private LocalDateTime createdAt;
    @NotBlank
    private String status;
    private List<OrderItemDto> orderItems;
}
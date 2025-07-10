package com.template.orders_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class OrderItemDto {
    private UUID id;
    @NotNull
    private UUID orderId;
    @NotNull
    private UUID productId;
    @Positive
    private int quantity;
    @NotNull
    private BigDecimal pricePerUnit;
}
package com.template.orders_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderUpdateDto {
    private String status;
    private List<OrderItemDto> orderItems;
}
package com.template.orders_service.mapper;

import com.template.orders_service.dto.OrderItemDto;
import com.template.orders_service.entity.Order;
import com.template.orders_service.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItemDto toDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setOrderId(item.getOrder().getId());
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setPricePerUnit(item.getPricePerUnit());
        return dto;
    }

    public OrderItem toEntity(OrderItemDto dto, Order order) {
        OrderItem item = new OrderItem();
        item.setId(dto.getId());
        item.setOrder(order);
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setPricePerUnit(dto.getPricePerUnit());
        return item;
    }
}
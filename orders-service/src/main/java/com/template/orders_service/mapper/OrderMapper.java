package com.template.orders_service.mapper;

import com.template.orders_service.dto.OrderDto;
import com.template.orders_service.entity.Order;
import com.template.orders_service.entity.OrderItem;
import com.template.orders_service.dto.OrderItemDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setStatus(order.getStatus());
        if (order.getOrderItems() != null) {
            dto.setOrderItems(order.getOrderItems().stream().map(this::toItemDto).collect(Collectors.toList()));
        }
        return dto;
    }

    public Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setUserId(dto.getUserId());
        order.setCreatedAt(dto.getCreatedAt());
        order.setStatus(dto.getStatus());
        // orderItems ustawiane osobno w serwisie
        return order;
    }

    public OrderItemDto toItemDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setOrderId(item.getOrder().getId());
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setPricePerUnit(item.getPricePerUnit());
        return dto;
    }

    public OrderItem toItemEntity(OrderItemDto dto, Order order) {
        OrderItem item = new OrderItem();
        item.setId(dto.getId());
        item.setOrder(order);
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setPricePerUnit(dto.getPricePerUnit());
        return item;
    }

    public List<OrderItemDto> toItemDtoList(List<OrderItem> items) {
        return items.stream().map(this::toItemDto).collect(Collectors.toList());
    }
}
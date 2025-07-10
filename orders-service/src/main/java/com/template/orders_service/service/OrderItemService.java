package com.template.orders_service.service;

import com.template.orders_service.dto.OrderItemDto;
import com.template.orders_service.entity.Order;
import com.template.orders_service.entity.OrderItem;
import com.template.orders_service.mapper.OrderItemMapper;
import com.template.orders_service.repository.OrderItemRepository;
import com.template.orders_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;

    @Transactional(readOnly = true)
    public List<OrderItemDto> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderItemDto getOrderItemById(UUID id) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found"));
        return orderItemMapper.toDto(item);
    }

    @Transactional
    public OrderItemDto createOrderItem(OrderItemDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found for item"));
        OrderItem item = orderItemMapper.toEntity(dto, order);
        item = orderItemRepository.save(item);
        return orderItemMapper.toDto(item);
    }

    @Transactional
    public OrderItemDto updateOrderItem(UUID id, OrderItemDto dto) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found"));
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found for item"));
        item.setOrder(order);
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setPricePerUnit(dto.getPricePerUnit());
        item = orderItemRepository.save(item);
        return orderItemMapper.toDto(item);
    }

    @Transactional
    public void deleteOrderItem(UUID id) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found"));
        orderItemRepository.delete(item);
    }
}
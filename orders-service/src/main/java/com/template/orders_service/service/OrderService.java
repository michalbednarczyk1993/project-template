package com.template.orders_service.service;

import com.template.orders_service.dto.OrderDto;
import com.template.orders_service.dto.OrderItemDto;
import com.template.orders_service.entity.Order;
import com.template.orders_service.entity.OrderItem;
import com.template.orders_service.mapper.OrderMapper;
import com.template.orders_service.repository.OrderRepository;
import com.template.orders_service.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    // Eager fetch orderItems to avoid LazyLoadingException
                    order.getOrderItems().size();
                    return orderMapper.toDto(order);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<OrderDto> getAllOrdersPaged(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(o -> {
                    o.getOrderItems().size();
                    return orderMapper.toDto(o);
                });
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.getOrderItems().size(); // Eager fetch
        return orderMapper.toDto(order);
    }

    @Transactional
    public OrderDto createOrder(OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        order = orderRepository.save(order);
        if (dto.getOrderItems() != null) {
            List<OrderItem> items = dto.getOrderItems().stream()
                    .map(itemDto -> orderMapper.toItemEntity(itemDto, order))
                    .collect(Collectors.toList());
            items.forEach(orderItemRepository::save);
            order.setOrderItems(items);
        }
        return orderMapper.toDto(order);
    }

    @Transactional
    public OrderDto updateOrder(UUID id, OrderDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setUserId(dto.getUserId());
        order.setCreatedAt(dto.getCreatedAt());
        order.setStatus(dto.getStatus());
        // Usuwamy stare pozycje i dodajemy nowe
        orderItemRepository.deleteAll(order.getOrderItems());
        if (dto.getOrderItems() != null) {
            List<OrderItem> items = dto.getOrderItems().stream()
                    .map(itemDto -> orderMapper.toItemEntity(itemDto, order))
                    .collect(Collectors.toList());
            items.forEach(orderItemRepository::save);
            order.setOrderItems(items);
        }
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Transactional
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        orderRepository.delete(order);
    }
}
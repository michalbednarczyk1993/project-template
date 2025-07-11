package com.template.orders_service.service;

import com.template.orders_service.dto.OrderDto;
import com.template.orders_service.dto.OrderItemDto;
import com.template.orders_service.dto.OrderUpdateDto;
import com.template.orders_service.entity.Order;
import com.template.orders_service.entity.OrderItem;
import com.template.orders_service.mapper.OrderMapper;
import com.template.orders_service.repository.OrderRepository;
import com.template.orders_service.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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
                    Hibernate.initialize(order.getOrderItems());
                    return orderMapper.toDto(order);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<OrderDto> getAllOrdersPaged(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(o -> {
                    Hibernate.initialize(o.getOrderItems());
                    return orderMapper.toDto(o);
                });
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        Hibernate.initialize(order.getOrderItems()); // Eager fetch
        return orderMapper.toDto(order);
    }

    @Transactional
    public OrderDto createOrder(OrderDto dto) {
        final Order order = orderRepository.save(orderMapper.toEntity(dto));
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
    public OrderDto updateOrder(UUID id, OrderUpdateDto dto) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        // Aktualizujemy tylko status, jeśli został przekazany
        if (dto.getStatus() != null) {
            order.setStatus(dto.getStatus());
        }
        // Aktualizujemy pozycje zamówienia, jeśli zostały przekazane
        if (dto.getOrderItems() != null) {
            // Usuwamy stare pozycje
            orderItemRepository.deleteAll(order.getOrderItems());
            // Dodajemy nowe pozycje
            List<OrderItem> items = dto.getOrderItems().stream()
                    .map(itemDto -> orderMapper.toItemEntity(itemDto, order))
                    .collect(Collectors.toList());
            items.forEach(orderItemRepository::save);
            order.setOrderItems(items);
        }
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        orderRepository.delete(order);
    }
}
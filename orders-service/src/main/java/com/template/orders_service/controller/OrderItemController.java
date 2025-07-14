package com.template.orders_service.controller;

import com.template.orders_service.dto.OrderItemDto;
import com.template.orders_service.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItemDto>> getAll() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @PostMapping
    public ResponseEntity<OrderItemDto> create(@Valid @RequestBody OrderItemDto dto) {
        return ResponseEntity.ok(orderItemService.createOrderItem(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDto> update(@PathVariable UUID id, @Valid @RequestBody OrderItemDto dto) {
        return ResponseEntity.ok(orderItemService.updateOrderItem(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
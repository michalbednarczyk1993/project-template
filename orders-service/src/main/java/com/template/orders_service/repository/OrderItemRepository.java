package com.template.orders_service.repository;

import com.template.orders_service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
package com.template.orders_service.repository;

import com.template.orders_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Override
    @EntityGraph(attributePaths = "orderItems")
    List<Order> findAll();

    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findById(UUID id);
}
package com.template.products_service.repository;

import com.template.products_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
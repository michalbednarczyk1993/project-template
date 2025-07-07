package com.template.products_service;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);
}
package com.template.products_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
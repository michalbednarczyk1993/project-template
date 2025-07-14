package com.template.products_service.mapper;

import com.template.products_service.entity.Product;
import com.template.products_service.dto.ProductDto;
import com.template.products_service.entity.Category;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        if (product.getImages() != null) {
            dto.setImageUrls(product.getImages().stream()
                    .map(img -> img.getImageUrl())
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Product toEntity(ProductDto dto, Category category) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        // images są mapowane osobno
        return product;
    }
}
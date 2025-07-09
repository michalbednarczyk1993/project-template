package com.template.products_service.mapper;

import com.template.products_service.entity.ProductImage;
import com.template.products_service.dto.ProductImageDto;
import com.template.products_service.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {
    public ProductImageDto toDto(ProductImage image) {
        ProductImageDto dto = new ProductImageDto();
        dto.setId(image.getId());
        dto.setProductId(image.getProduct() != null ? image.getProduct().getId() : null);
        dto.setImageUrl(image.getImageUrl());
        return dto;
    }

    public ProductImage toEntity(ProductImageDto dto, Product product) {
        ProductImage image = new ProductImage();
        image.setId(dto.getId());
        image.setProduct(product);
        image.setImageUrl(dto.getImageUrl());
        return image;
    }
}
package com.template.products_service.service;

import com.template.products_service.repository.ProductRepository;
import com.template.products_service.repository.CategoryRepository;
import com.template.products_service.entity.Product;
import com.template.products_service.entity.Category;
import com.template.products_service.dto.ProductDto;
import com.template.products_service.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProductDto> getProductById(UUID id) {
        return productRepository.findById(id).map(productMapper::toDto);
    }

    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        Category category = null;
        if (productDto.getCategoryId() != null) {
            category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        }
        Product product = productMapper.toEntity(productDto, category);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
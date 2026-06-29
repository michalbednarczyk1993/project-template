package com.template.products_service.modules.product.api;

import com.template.products_service.modules.product.domain.Product;
import com.template.products_service.modules.product.domain.ProductVariant;
import java.util.*;

public interface ProductCatalog {
    Optional<Product> findById(UUID id);
    Optional<ProductVariant> findVariantById(UUID variantId);
    ProductVariant defaultVariantFor(UUID productId);
    List<ProductVariant> variantsFor(UUID productId);
    List<Product> searchByName(String query);
    Product createProduct(Product product);
    void updateProduct(Product product);
    void setDefaultVariant(UUID productId, UUID variantId);
}

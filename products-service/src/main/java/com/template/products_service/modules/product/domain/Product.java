package com.template.products_service.modules.product.domain;

import com.template.products_service.modules.quantity.domain.Unit;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Product(UUID id, String name, String category, Unit defaultUnit, UUID defaultVariantId,
                      List<ProductVariant> variants, Instant createdAt, Instant updatedAt) {
    public ProductVariant defaultVariant() {
        return variants.stream()
                .filter(ProductVariant::defaultVariant)
                .findFirst()
                .orElseGet(() -> variants.isEmpty() ? null : variants.getFirst());
    }
}

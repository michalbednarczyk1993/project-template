package com.template.products_service.modules.product.domain;

import com.template.products_service.modules.quantity.domain.ProductMeasurementProfile;
import java.time.Instant;
import java.util.UUID;

public record ProductVariant(UUID id, UUID productId, String name, boolean defaultVariant,
                             ProductMeasurementProfile measurementProfile, NutritionPer100g nutritionPer100g,
                             Instant createdAt, Instant updatedAt) {}

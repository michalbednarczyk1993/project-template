package com.template.products_service.modules.pricing.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record IngredientNutritionBreakdown(UUID ingredientId, UUID productId, BigDecimal massG, BigDecimal calories, String warning) {}

package com.template.products_service.modules.product.domain;

import java.math.BigDecimal;

public record NutritionPer100g(BigDecimal calories, BigDecimal protein, BigDecimal fat, BigDecimal carbs) {}

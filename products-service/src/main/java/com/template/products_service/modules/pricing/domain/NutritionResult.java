package com.template.products_service.modules.pricing.domain;

import java.math.BigDecimal;
import java.util.List;

public record NutritionResult(BigDecimal totalCalories, BigDecimal caloriesPerServing,
                              BigDecimal totalProtein, BigDecimal proteinPerServing,
                              BigDecimal totalFat, BigDecimal fatPerServing,
                              BigDecimal totalCarbs, BigDecimal carbsPerServing,
                              List<String> warnings, List<IngredientNutritionBreakdown> breakdowns) {}

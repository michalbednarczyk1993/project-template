package com.template.products_service.modules.pricing.api;

import com.template.products_service.modules.pricing.domain.NutritionResult;
import java.util.UUID;

public interface NutritionCalculator { NutritionResult calculateRecipe(UUID recipeId, int targetServings); }

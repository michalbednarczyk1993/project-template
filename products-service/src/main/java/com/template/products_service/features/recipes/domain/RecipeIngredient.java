package com.template.products_service.features.recipes.domain;

import com.template.products_service.modules.quantity.domain.Quantity;
import java.util.UUID;

public record RecipeIngredient(UUID id, UUID recipeId, UUID productId, UUID productVariantId,
                               Quantity originalQuantity, int displayOrder, String note) {}

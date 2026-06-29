package com.template.products_service.features.recipes.domain;

import java.util.*;

public interface RecipeIngredientRepository {
    List<RecipeIngredient> findByRecipeId(UUID recipeId);
    void saveAll(UUID recipeId, List<RecipeIngredient> ingredients);
}

package com.template.products_service.modules.pricing;

import com.template.products_service.features.recipes.domain.*;
import com.template.products_service.modules.pricing.application.RecipeNutritionCalculator;
import com.template.products_service.modules.product.data.InMemoryProductCatalog;
import com.template.products_service.modules.quantity.application.DefaultQuantityConverter;
import com.template.products_service.modules.quantity.domain.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeNutritionCalculatorTest {
    @Test void calculatesCaloriesAndReportsMissingDataWarnings() {
        var catalog = new InMemoryProductCatalog();
        var flour = catalog.searchByName("mąka").getFirst(); var ketchup = catalog.searchByName("ketchup").getFirst();
        UUID recipeId = UUID.randomUUID();
        Recipe recipe = new Recipe(recipeId, "Test", null, null, 4, "", null, null, Instant.now(), Instant.now());
        List<RecipeIngredient> rows = List.of(
                new RecipeIngredient(UUID.randomUUID(), recipeId, flour.id(), null, new Quantity(200, Unit.GRAM), 1, null),
                new RecipeIngredient(UUID.randomUUID(), recipeId, ketchup.id(), null, new Quantity(1, Unit.packageUnit("opakowanie", "opakowanie")), 2, null));
        RecipeIngredientRepository repo = new RecipeIngredientRepository() { public List<RecipeIngredient> findByRecipeId(UUID id) { return rows; } public void saveAll(UUID id, List<RecipeIngredient> ingredients) {} };
        var result = new RecipeNutritionCalculator(recipe, repo, catalog, new DefaultQuantityConverter()).calculateRecipe(recipeId, 6);
        assertEquals(new BigDecimal("1092.00"), result.totalCalories());
        assertEquals(new BigDecimal("182.00"), result.caloriesPerServing());
        assertTrue(result.warnings().stream().anyMatch(w -> w.contains("ketchup")));
    }
    @Test void selectedVariantChangesMassAndCalories() {
        var catalog = new InMemoryProductCatalog();
        var egg = catalog.searchByName("jajko").getFirst();
        var eggL = catalog.variantsFor(egg.id()).stream()
                .filter(variant -> variant.name().equals("L"))
                .findFirst()
                .orElseThrow();
        UUID recipeId = UUID.randomUUID();
        Recipe recipe = new Recipe(recipeId, "Jajecznica", null, null, 1, "", null, null, Instant.now(), Instant.now());
        List<RecipeIngredient> rows = List.of(new RecipeIngredient(UUID.randomUUID(), recipeId, egg.id(), eggL.id(),
                new Quantity(2, Unit.PIECE), 1, "wyjątkowo jajka L"));
        RecipeIngredientRepository repo = new RecipeIngredientRepository() {
            public List<RecipeIngredient> findByRecipeId(UUID id) { return rows; }
            public void saveAll(UUID id, List<RecipeIngredient> ingredients) {}
        };

        var result = new RecipeNutritionCalculator(recipe, repo, catalog, new DefaultQuantityConverter()).calculateRecipe(recipeId, 1);

        assertEquals(new BigDecimal("180.18"), result.totalCalories());
        assertTrue(result.warnings().isEmpty());
    }

}

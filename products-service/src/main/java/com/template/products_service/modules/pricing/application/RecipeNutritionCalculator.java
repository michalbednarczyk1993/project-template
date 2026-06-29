package com.template.products_service.modules.pricing.application;

import com.template.products_service.features.recipes.domain.*;
import com.template.products_service.modules.pricing.api.NutritionCalculator;
import com.template.products_service.modules.pricing.domain.*;
import com.template.products_service.modules.product.api.ProductCatalog;
import com.template.products_service.modules.product.domain.ProductVariant;
import com.template.products_service.modules.quantity.api.QuantityConverter;
import com.template.products_service.modules.quantity.domain.*;
import java.math.*;
import java.util.*;

public class RecipeNutritionCalculator implements NutritionCalculator {
    private final Recipe recipe;
    private final RecipeIngredientRepository ingredients;
    private final ProductCatalog products;
    private final QuantityConverter converter;

    public RecipeNutritionCalculator(Recipe recipe, RecipeIngredientRepository ingredients,
                                     ProductCatalog products, QuantityConverter converter) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.products = products;
        this.converter = converter;
    }

    public NutritionResult calculateRecipe(UUID recipeId, int targetServings) {
        List<String> warnings = new ArrayList<>();
        List<IngredientNutritionBreakdown> breakdowns = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (RecipeIngredient ingredient : ingredients.findByRecipeId(recipeId)) {
            Optional<ProductVariant> variant = selectedVariant(ingredient);
            if (variant.isEmpty()) {
                warnings.add("Brak wariantu produktu dla składnika: " + ingredient.productId());
                continue;
            }
            ProductVariant productVariant = variant.get();
            Quantity scaled = converter.scale(ingredient.originalQuantity(), recipe.baseServings(), targetServings).scaled();
            ConversionResult grams = converter.convert(scaled, Unit.GRAM, productVariant.measurementProfile());
            if (!grams.convertible()) {
                warnings.add("Nie można przeliczyć ilości na masę dla wariantu: " + productVariant.name());
                breakdowns.add(new IngredientNutritionBreakdown(ingredient.id(), productVariant.productId(), null, null, grams.warning()));
                continue;
            }
            if (productVariant.nutritionPer100g() == null || productVariant.nutritionPer100g().calories() == null) {
                warnings.add("Brak kaloryczności dla wariantu: " + productVariant.name());
                breakdowns.add(new IngredientNutritionBreakdown(ingredient.id(), productVariant.productId(), grams.quantity().value(), null, "missing calories"));
                continue;
            }
            BigDecimal kcal = grams.quantity().value()
                    .multiply(productVariant.nutritionPer100g().calories())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            total = total.add(kcal);
            breakdowns.add(new IngredientNutritionBreakdown(ingredient.id(), productVariant.productId(), grams.quantity().value(), kcal, null));
        }
        return new NutritionResult(total, total.divide(BigDecimal.valueOf(targetServings), 2, RoundingMode.HALF_UP),
                null, null, null, null, null, null, warnings, breakdowns);
    }

    private Optional<ProductVariant> selectedVariant(RecipeIngredient ingredient) {
        if (ingredient.productVariantId() != null) {
            return products.findVariantById(ingredient.productVariantId());
        }
        return Optional.ofNullable(products.defaultVariantFor(ingredient.productId()));
    }
}

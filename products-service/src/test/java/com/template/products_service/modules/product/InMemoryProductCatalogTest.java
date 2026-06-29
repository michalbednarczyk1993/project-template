package com.template.products_service.modules.product;

import com.template.products_service.modules.product.data.InMemoryProductCatalog;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductCatalogTest {
    @Test void milkVariantsShareCanonicalProductAndHaveDifferentNutrition() {
        var catalog = new InMemoryProductCatalog();
        var milk = catalog.searchByName("mleko").getFirst();
        var variants = catalog.variantsFor(milk.id());

        assertEquals(2, variants.size());
        assertTrue(variants.stream().allMatch(variant -> variant.productId().equals(milk.id())));
        assertEquals(new BigDecimal("61"), catalog.defaultVariantFor(milk.id()).nutritionPer100g().calories());

        var lowFat = variants.stream().filter(variant -> variant.name().contains("0.5%")).findFirst().orElseThrow();
        catalog.setDefaultVariant(milk.id(), lowFat.id());

        assertEquals(lowFat.id(), catalog.defaultVariantFor(milk.id()).id());
        assertEquals(new BigDecimal("38"), catalog.defaultVariantFor(milk.id()).nutritionPer100g().calories());
    }
}

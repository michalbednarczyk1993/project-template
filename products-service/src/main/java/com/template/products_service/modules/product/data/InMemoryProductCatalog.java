package com.template.products_service.modules.product.data;

import com.template.products_service.modules.product.api.ProductCatalog;
import com.template.products_service.modules.product.domain.*;
import com.template.products_service.modules.quantity.domain.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public class InMemoryProductCatalog implements ProductCatalog {
    private final Map<UUID, Product> products = new LinkedHashMap<>();
    private final Map<UUID, ProductVariant> variants = new LinkedHashMap<>();

    public InMemoryProductCatalog() {
        seedDefaults();
    }

    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public Optional<ProductVariant> findVariantById(UUID variantId) {
        return Optional.ofNullable(variants.get(variantId));
    }

    public ProductVariant defaultVariantFor(UUID productId) {
        return findById(productId).map(Product::defaultVariant).orElse(null);
    }

    public List<ProductVariant> variantsFor(UUID productId) {
        return findById(productId).map(Product::variants).orElse(List.of());
    }

    public List<Product> searchByName(String query) {
        String normalized = query.toLowerCase(Locale.ROOT);
        return products.values().stream()
                .filter(product -> product.name().toLowerCase(Locale.ROOT).contains(normalized))
                .toList();
    }

    public Product createProduct(Product product) {
        products.put(product.id(), product);
        product.variants().forEach(variant -> variants.put(variant.id(), variant));
        return product;
    }

    public void updateProduct(Product product) {
        products.put(product.id(), product);
        product.variants().forEach(variant -> variants.put(variant.id(), variant));
    }

    public void setDefaultVariant(UUID productId, UUID variantId) {
        Product product = products.get(productId);
        if (product == null || variants.get(variantId) == null) {
            throw new IllegalArgumentException("Unknown product or variant");
        }
        List<ProductVariant> updated = product.variants().stream()
                .map(variant -> new ProductVariant(
                        variant.id(),
                        variant.productId(),
                        variant.name(),
                        variant.id().equals(variantId),
                        variant.measurementProfile(),
                        variant.nutritionPer100g(),
                        variant.createdAt(),
                        Instant.now()))
                .toList();
        updateProduct(new Product(product.id(), product.name(), product.category(), product.defaultUnit(), variantId,
                updated, product.createdAt(), Instant.now()));
    }

    private void seedDefaults() {
        addProduct("woda", "płyny", Unit.MILLILITER, List.of(
                variant("woda", "domyślna", true, "1.0", "0", null,
                        Map.of("szklanka", new Quantity(250, Unit.MILLILITER)), Map.of())));
        addProduct("mleko", "nabiał", Unit.MILLILITER, List.of(
                variant("mleko", "3.2% tłuszczu", true, "1.03", "61", null,
                        Map.of("szklanka", new Quantity(250, Unit.MILLILITER)), Map.of("karton", new Quantity(1000, Unit.MILLILITER))),
                variant("mleko", "0.5% tłuszczu", false, "1.035", "38", null,
                        Map.of("szklanka", new Quantity(250, Unit.MILLILITER)), Map.of("karton", new Quantity(1000, Unit.MILLILITER)))));
        addProduct("masło", "nabiał", Unit.GRAM, List.of(
                variant("masło", "kostka 200 g", true, null, "720", null,
                        Map.of(), Map.of("kostka", new Quantity(200, Unit.GRAM)))));
        addProduct("mąka pszenna", "suche", Unit.GRAM, List.of(
                variant("mąka pszenna", "domyślna", true, null, "364", null,
                        Map.of("szklanka", new Quantity(160, Unit.GRAM)), Map.of())));
        addProduct("cukier", "suche", Unit.GRAM, List.of(
                variant("cukier", "domyślna", true, null, "400", null,
                        Map.of("szklanka", new Quantity(220, Unit.GRAM), "łyżka", new Quantity(12, Unit.GRAM), "łyżeczka", new Quantity(5, Unit.GRAM)), Map.of())));
        addProduct("jajko", "nabiał", Unit.PIECE, List.of(
                variant("jajko", "M", true, null, "143", "56", Map.of(), Map.of()),
                variant("jajko", "S", false, null, "143", "43", Map.of(), Map.of()),
                variant("jajko", "L", false, null, "143", "63", Map.of(), Map.of())));
        addProduct("ryż", "suche", Unit.GRAM, List.of(variant("ryż", "domyślna", true, null, "350", null, Map.of(), Map.of())));
        addProduct("makaron", "suche", Unit.GRAM, List.of(variant("makaron", "domyślna", true, null, "360", null, Map.of(), Map.of())));
        addProduct("oliwa", "tłuszcze", Unit.MILLILITER, List.of(variant("oliwa", "domyślna", true, "0.91", "884", null, Map.of("łyżka", new Quantity(15, Unit.MILLILITER)), Map.of())));
        addProduct("ketchup", "dodatki", Unit.GRAM, List.of(variant("ketchup", "bez zdefiniowanego opakowania", true, null, null, null, Map.of(), Map.of())));
        addProduct("śmietanka", "nabiał", Unit.MILLILITER, List.of(variant("śmietanka", "30%", true, "1.0", "292", null, Map.of(), Map.of())));
    }

    private ProductVariant variant(String productName, String name, boolean defaultVariant, String density, String kcal,
                                   String pieceG, Map<String, Quantity> household, Map<String, Quantity> packages) {
        UUID productId = stableId(productName);
        UUID variantId = stableId(productName + ":" + name);
        Instant now = Instant.now();
        var profile = new ProductMeasurementProfile(productId, density == null ? null : new BigDecimal(density),
                pieceG == null ? null : new BigDecimal(pieceG), household, packages);
        return new ProductVariant(variantId, productId, name, defaultVariant, profile,
                new NutritionPer100g(kcal == null ? null : new BigDecimal(kcal), null, null, null), now, now);
    }

    private void addProduct(String name, String category, Unit unit, List<ProductVariant> productVariants) {
        UUID productId = stableId(name);
        productVariants.forEach(variant -> variants.put(variant.id(), variant));
        UUID defaultVariantId = productVariants.stream()
                .filter(ProductVariant::defaultVariant)
                .findFirst()
                .map(ProductVariant::id)
                .orElse(productVariants.getFirst().id());
        products.put(productId, new Product(productId, name, category, unit, defaultVariantId,
                productVariants, Instant.now(), Instant.now()));
    }

    private UUID stableId(String value) {
        return UUID.nameUUIDFromBytes(value.getBytes());
    }
}

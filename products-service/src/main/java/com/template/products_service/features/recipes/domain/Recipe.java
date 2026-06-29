package com.template.products_service.features.recipes.domain;

import java.time.Instant;
import java.util.UUID;

public record Recipe(UUID id, String title, UUID categoryId, String imagePath, int baseServings,
                     String instructions, String notes, String legacyIngredientsText, Instant createdAt, Instant updatedAt) {
    public Recipe { if (baseServings <= 0) throw new IllegalArgumentException("baseServings must be positive"); }
}

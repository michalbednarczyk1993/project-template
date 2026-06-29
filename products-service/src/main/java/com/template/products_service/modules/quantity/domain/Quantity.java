package com.template.products_service.modules.quantity.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Quantity(BigDecimal value, Unit unit) {
    public Quantity(double value, Unit unit) { this(BigDecimal.valueOf(value), unit); }
    public Quantity scale(int baseServings, int targetServings) {
        if (baseServings <= 0 || targetServings <= 0) throw new IllegalArgumentException("Servings must be positive");
        return new Quantity(value.multiply(BigDecimal.valueOf(targetServings)).divide(BigDecimal.valueOf(baseServings), 6, RoundingMode.HALF_UP).stripTrailingZeros(), unit);
    }
}

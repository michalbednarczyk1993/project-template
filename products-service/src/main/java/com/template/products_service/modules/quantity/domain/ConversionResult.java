package com.template.products_service.modules.quantity.domain;

public record ConversionResult(boolean convertible, Quantity quantity, String warning) {
    public static ConversionResult success(Quantity quantity) { return new ConversionResult(true, quantity, null); }
    public static ConversionResult failure(String warning) { return new ConversionResult(false, null, warning); }
}

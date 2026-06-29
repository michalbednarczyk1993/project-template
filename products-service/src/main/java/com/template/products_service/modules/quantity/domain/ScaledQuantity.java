package com.template.products_service.modules.quantity.domain;

public record ScaledQuantity(Quantity original, Quantity scaled, int baseServings, int targetServings) {}

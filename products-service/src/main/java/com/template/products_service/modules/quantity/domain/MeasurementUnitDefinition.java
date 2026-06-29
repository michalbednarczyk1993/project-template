package com.template.products_service.modules.quantity.domain;

import java.time.Instant;
import java.util.UUID;

public record MeasurementUnitDefinition(UUID id, UUID productId, UUID productVariantId, String name,
                                        UnitType unitType, Quantity quantity, boolean defaultDefinition,
                                        Instant createdAt, Instant updatedAt) {}

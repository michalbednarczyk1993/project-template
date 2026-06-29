package com.template.products_service.modules.quantity.domain;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record ProductMeasurementProfile(UUID productId, BigDecimal densityGPerMl, BigDecimal averagePieceWeightG,
                                        Map<String, Quantity> householdUnits, Map<String, Quantity> packageUnits) {
    public Quantity definitionFor(Unit unit) {
        if (unit.type() == UnitType.HOUSEHOLD) return householdUnits.get(unit.code());
        if (unit.type() == UnitType.PACKAGE) return packageUnits.get(unit.code());
        return null;
    }
}

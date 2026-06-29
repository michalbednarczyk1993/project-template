package com.template.products_service.modules.quantity.application;

import com.template.products_service.modules.quantity.api.QuantityConverter;
import com.template.products_service.modules.quantity.domain.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class DefaultQuantityConverter implements QuantityConverter {
    private static final Map<String, BigDecimal> MASS_TO_G = Map.of("mg", new BigDecimal("0.001"), "g", BigDecimal.ONE, "kg", new BigDecimal("1000"));
    private static final Map<String, BigDecimal> VOLUME_TO_ML = Map.of("ml", BigDecimal.ONE, "l", new BigDecimal("1000"));

    public ConversionResult convert(Quantity source, Unit targetUnit, ProductMeasurementProfile profile) {
        if (source.unit().equals(targetUnit)) return ConversionResult.success(source);
        Quantity normalized = normalizeDefinedUnit(source, profile);
        if (normalized == null) return ConversionResult.failure("Nie znam wielkości jednostki: " + source.unit().displayName());
        if (isMass(normalized.unit()) && isMass(targetUnit)) return ConversionResult.success(new Quantity(toBase(normalized, MASS_TO_G).divide(MASS_TO_G.get(targetUnit.code()), 6, RoundingMode.HALF_UP).stripTrailingZeros(), targetUnit));
        if (isVolume(normalized.unit()) && isVolume(targetUnit)) return ConversionResult.success(new Quantity(toBase(normalized, VOLUME_TO_ML).divide(VOLUME_TO_ML.get(targetUnit.code()), 6, RoundingMode.HALF_UP).stripTrailingZeros(), targetUnit));
        if (isVolume(normalized.unit()) && isMass(targetUnit)) {
            if (profile == null || profile.densityGPerMl() == null) return ConversionResult.failure("Brak gęstości produktu do konwersji ml ↔ g");
            BigDecimal grams = toBase(normalized, VOLUME_TO_ML).multiply(profile.densityGPerMl());
            return convert(new Quantity(grams, Unit.GRAM), targetUnit, profile);
        }
        if (isMass(normalized.unit()) && isVolume(targetUnit)) {
            if (profile == null || profile.densityGPerMl() == null) return ConversionResult.failure("Brak gęstości produktu do konwersji g ↔ ml");
            BigDecimal ml = toBase(normalized, MASS_TO_G).divide(profile.densityGPerMl(), 6, RoundingMode.HALF_UP);
            return convert(new Quantity(ml, Unit.MILLILITER), targetUnit, profile);
        }
        if (normalized.unit().type() == UnitType.COUNT && isMass(targetUnit)) {
            if (profile == null || profile.averagePieceWeightG() == null) return ConversionResult.failure("Brak masy sztuki produktu");
            return convert(new Quantity(normalized.value().multiply(profile.averagePieceWeightG()), Unit.GRAM), targetUnit, profile);
        }
        return ConversionResult.failure("Nieobsługiwana konwersja z " + source.unit().displayName() + " na " + targetUnit.displayName());
    }
    public ScaledQuantity scale(Quantity source, int baseServings, int targetServings) { return new ScaledQuantity(source, source.scale(baseServings, targetServings), baseServings, targetServings); }
    private Quantity normalizeDefinedUnit(Quantity q, ProductMeasurementProfile p) { if (q.unit().type()!=UnitType.HOUSEHOLD && q.unit().type()!=UnitType.PACKAGE) return q; if (p==null) return null; Quantity def=p.definitionFor(q.unit()); return def==null?null:new Quantity(q.value().multiply(def.value()), def.unit()); }
    private boolean isMass(Unit u) { return MASS_TO_G.containsKey(u.code()); }
    private boolean isVolume(Unit u) { return VOLUME_TO_ML.containsKey(u.code()); }
    private BigDecimal toBase(Quantity q, Map<String, BigDecimal> factor) { return q.value().multiply(factor.get(q.unit().code())); }
}

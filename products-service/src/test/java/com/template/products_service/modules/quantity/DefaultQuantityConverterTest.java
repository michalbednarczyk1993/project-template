package com.template.products_service.modules.quantity;

import com.template.products_service.modules.quantity.application.DefaultQuantityConverter;
import com.template.products_service.modules.quantity.domain.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class DefaultQuantityConverterTest {
    private final DefaultQuantityConverter converter = new DefaultQuantityConverter();
    @Test void convertsGramsAndKilograms() { assertEquals(new BigDecimal("1"), converter.convert(new Quantity(1000, Unit.GRAM), Unit.KILOGRAM, null).quantity().value()); assertEquals(new BigDecimal("500"), converter.convert(new Quantity(0.5, Unit.KILOGRAM), Unit.GRAM, null).quantity().value()); }
    @Test void convertsMillilitersAndLiters() { assertEquals(new BigDecimal("1"), converter.convert(new Quantity(1000, Unit.MILLILITER), Unit.LITER, null).quantity().value()); assertEquals(new BigDecimal("250"), converter.convert(new Quantity(0.25, Unit.LITER), Unit.MILLILITER, null).quantity().value()); }
    @Test void convertsWaterMillilitersToGramsWithDensity() { var profile = new ProductMeasurementProfile(UUID.randomUUID(), BigDecimal.ONE, null, Map.of(), Map.of()); assertEquals(new BigDecimal("250"), converter.convert(new Quantity(250, Unit.MILLILITER), Unit.GRAM, profile).quantity().value()); }
    @Test void failsVolumeToMassWithoutDensity() { var result = converter.convert(new Quantity(250, Unit.MILLILITER), Unit.GRAM, null); assertFalse(result.convertible()); assertTrue(result.warning().contains("Brak gęstości")); }
    @Test void scalesServings() { assertEquals(new BigDecimal("300"), converter.scale(new Quantity(200, Unit.GRAM), 4, 6).scaled().value()); }
    @Test void convertsDefinedHouseholdUnit() { var glass = Unit.household("szklanka", "szklanka"); var profile = new ProductMeasurementProfile(UUID.randomUUID(), BigDecimal.ONE, null, Map.of("szklanka", new Quantity(250, Unit.MILLILITER)), Map.of()); assertEquals(new BigDecimal("250"), converter.convert(new Quantity(1, glass), Unit.GRAM, profile).quantity().value()); }
    @Test void unknownPackageIsNotGuessed() { var pack = Unit.packageUnit("opakowanie", "opakowanie"); assertFalse(converter.convert(new Quantity(1, pack), Unit.GRAM, new ProductMeasurementProfile(UUID.randomUUID(), null, null, Map.of(), Map.of())).convertible()); }
}

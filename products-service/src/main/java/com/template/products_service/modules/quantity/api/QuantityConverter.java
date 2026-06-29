package com.template.products_service.modules.quantity.api;

import com.template.products_service.modules.quantity.domain.*;

public interface QuantityConverter {
    ConversionResult convert(Quantity source, Unit targetUnit, ProductMeasurementProfile profile);
    ScaledQuantity scale(Quantity source, int baseServings, int targetServings);
}

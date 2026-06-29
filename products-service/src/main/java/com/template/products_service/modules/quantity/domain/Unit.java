package com.template.products_service.modules.quantity.domain;

public record Unit(String code, String displayName, UnitType type) {
    public static final Unit MILLIGRAM = new Unit("mg", "mg", UnitType.MASS);
    public static final Unit GRAM = new Unit("g", "g", UnitType.MASS);
    public static final Unit KILOGRAM = new Unit("kg", "kg", UnitType.MASS);
    public static final Unit MILLILITER = new Unit("ml", "ml", UnitType.VOLUME);
    public static final Unit LITER = new Unit("l", "l", UnitType.VOLUME);
    public static final Unit PIECE = new Unit("szt", "szt.", UnitType.COUNT);
    public static Unit household(String code, String displayName) { return new Unit(code, displayName, UnitType.HOUSEHOLD); }
    public static Unit packageUnit(String code, String displayName) { return new Unit(code, displayName, UnitType.PACKAGE); }
}

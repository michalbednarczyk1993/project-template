# Model danych

`Recipe` ma `baseServings` i zachowuje `legacyIngredientsText` wyłącznie jako tekst starego formatu. Nowa encja `RecipeIngredient` przechowuje `productId`, oryginalną `Quantity`, kolejność i opcjonalną notatkę.

`Product` zawiera nazwę, kategorię, jednostkę domyślną, `ProductMeasurementProfile` oraz `NutritionPer100g`. Jednostki opakowaniowe i domowe są definicjami per produkt, dlatego `1 kostka masła` może oznaczać `200 g`, a `1 opakowanie ketchupu` pozostaje nieznane.

## Edytowalne warianty i definicje jednostek

Produkty spożywcze są modelowane jako produkt kanoniczny oraz warianty produktu. `mleko` pozostaje jednym produktem, ale może mieć wariant `3.2% tłuszczu` i `0.5% tłuszczu`; jeden wariant jest domyślny, a składnik przepisu może wskazać wariant użyty wyjątkowo. Analogicznie `jajko` może mieć warianty `S`, `M`, `L` z inną średnią masą sztuki.

Domyślne wielkości jednostek domowych i opakowaniowych są rekordami w tabeli `measurement_unit_definitions`, a nie stałymi zaszytymi w UI. Dzięki temu `szklanka` może mieć np. 200 ml albo 250 ml, zależnie od definicji użytkownika lub produktu.

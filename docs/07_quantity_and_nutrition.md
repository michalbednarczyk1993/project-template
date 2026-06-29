# Ilości, skalowanie i kaloryczność

Skalowanie porcji używa wzoru `originalAmount * targetServings / baseServings` i nie nadpisuje wartości bazowej przepisu.

Konwersje obsługiwane jawnie:

- `g ↔ kg` oraz `ml ↔ l` zawsze.
- `ml ↔ g` tylko z gęstością produktu.
- `szt. ↔ g` tylko ze średnią masą sztuki.
- jednostki domowe/opakowaniowe tylko wtedy, gdy produkt ma definicję tej jednostki.

Kaloryczność jest liczona tylko wtedy, gdy składnik da się przeliczyć na gramy i produkt ma `caloriesPer100g`. Braki danych są zwracane jako ostrzeżenia. Wartości startowe są przykładowe/użytkowe i nie są poradą medyczną.

## Warianty produktów i edycja domyślnych wielkości

Katalog rozróżnia produkt kanoniczny i wariant produktu. Kalkulator używa wariantu wskazanego w `RecipeIngredient.productVariantId`, a jeśli go nie podano — wariantu domyślnego produktu. Zmiana domyślnego wariantu, np. mleka z `3.2%` na `0.5%`, zmienia gęstość i kaloryczność używaną w nowych kalkulacjach.

Definicje `szklanka`, `kostka`, `karton` i podobne są rekordami danych. Nie należy zgadywać pojemności: gdy nie ma definicji dla produktu lub wariantu, konwersja zwraca ostrzeżenie.

# Architektura

Etap 2 wprowadza trzy moduły domenowe w `products-service/src/main/java/com/template/products_service/modules`: `product`, `quantity` i `pricing`. Moduły wystawiają interfejsy API i nie zależą od warstwy prezentacji.

- `product` przechowuje katalog produktów spożywczych, profile pomiarowe i przykładowe wartości odżywcze.
- `quantity` odpowiada za ilości, jednostki, skalowanie porcji i jawne konwersje.
- `pricing` jest użyty jako archetyp kalkulacji: komponenty przepisu są przeliczane na wynik żywieniowy.
